package InterfazGrafica;

import DAO.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionarOTFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filtroEstadoCombo;
    private JComboBox<String> filtroPrioridadCombo;
    private OrdenTrabajoDAO otDAO = new OrdenTrabajoDAO();
    private TecnicoDAO tecnicoDAO = new TecnicoDAO();

    public GestionarOTFrame() {
        setTitle("Gestión de Órdenes de Trabajo");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Filtrar por estado:"));
        filtroEstadoCombo = new JComboBox<>();
        filtroEstadoCombo.addItem("TODOS");
        for (EstadoOrden estado : EstadoOrden.values()) {
            filtroEstadoCombo.addItem(estado.name());
        }
        topPanel.add(filtroEstadoCombo);

        topPanel.add(new JLabel("Filtrar por prioridad:"));
        filtroPrioridadCombo = new JComboBox<>(new String[]{"TODAS", "Alta", "Media", "Baja"});
        topPanel.add(filtroPrioridadCombo);

        JButton aplicarFiltrosBtn = new JButton("Aplicar Filtros");
        aplicarFiltrosBtn.addActionListener(e -> cargarOTs());
        topPanel.add(aplicarFiltrosBtn);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{
            "N° OT", "Trámite", "Prioridad", "Estado", "Responsable",
            "Técnico", "Problema", "Recurso", "Asignación", "Finalización"
        });

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton modificarOTsBtn = new JButton("Modificar OT");
        modificarOTsBtn.addActionListener(e -> modificarOT());
        bottomPanel.add(modificarOTsBtn);

        JButton eliminarCerradasBtn = new JButton("Eliminar OTs");
        eliminarCerradasBtn.addActionListener(e -> eliminarOTsCerradas());
        bottomPanel.add(eliminarCerradasBtn);

        add(bottomPanel, BorderLayout.SOUTH);
        cargarOTs();
        setVisible(true);
    }

    private void cargarOTs() {
        tableModel.setRowCount(0);
        List<OrdenTrabajo> ots = otDAO.obtenerTodas();

        String filtroEstado = (String) filtroEstadoCombo.getSelectedItem();
        if (!"TODOS".equals(filtroEstado)) {
            ots = ots.stream()
                     .filter(ot -> ot.getEstado().name().equals(filtroEstado))
                     .collect(Collectors.toList());
        }

        String filtroPrioridad = (String) filtroPrioridadCombo.getSelectedItem();
        if (!"TODAS".equals(filtroPrioridad)) {
            ots = ots.stream()
                     .filter(ot -> ot.getPrioridad().equalsIgnoreCase(filtroPrioridad))
                     .collect(Collectors.toList());
        }

        ots.sort(Comparator.comparingInt(ot -> {
            switch (ot.getPrioridad().toLowerCase()) {
                case "alta": return 0;
                case "media": return 1;
                case "baja": return 2;
                default: return 3;
            }
        }));

        for (OrdenTrabajo ot : ots) {
            String fechaFinal = "";
            if (ot.getEstado() == EstadoOrden.FINALIZADO || ot.getEstado() == EstadoOrden.CANCELADO) {
                fechaFinal = ot.getFechaFinalizacion() != null ? ot.getFechaFinalizacion().toString() : "";
            }
            tableModel.addRow(new Object[]{
                ot.getNumero(), ot.getNumeroTramite(), ot.getPrioridad(),
                ot.getEstado().name(), ot.getResponsable(),
                ot.getTecnicoAsignado().getNombre(), ot.getProblema(),
                ot.getRecurso(), ot.getFechaAsignacion(), fechaFinal
            });
        }
    }

    private void modificarOT() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una OT.");
            return;
        }

        String estadoActual = (String) tableModel.getValueAt(fila, 3);
        if (estadoActual.equals("FINALIZADO") || estadoActual.equals("CANCELADO")) {
            JOptionPane.showMessageDialog(this, "No se puede modificar una OT FINALIZADA o CANCELADA.");
            return;
        }

        int numeroOT = (int) tableModel.getValueAt(fila, 0);
        String prioridad = (String) tableModel.getValueAt(fila, 2);
        OrdenTrabajo ot = otDAO.obtenerPorNumero(numeroOT);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JComboBox<String> prioridadCombo = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        prioridadCombo.setSelectedItem(prioridad);
        JComboBox<EstadoOrden> estadoCombo = new JComboBox<>(EstadoOrden.values());
        estadoCombo.setSelectedItem(EstadoOrden.valueOf(estadoActual));

        JComboBox<Tecnico> tecnicoCombo = new JComboBox<>();
        for (Tecnico t : tecnicoDAO.obtenerTodos()) {
            tecnicoCombo.addItem(t);
        }
        tecnicoCombo.setSelectedItem(ot.getTecnicoAsignado());

        JTextField recursoField = new JTextField(ot.getRecurso());

        panel.add(new JLabel("Nueva prioridad:"));
        panel.add(prioridadCombo);
        panel.add(new JLabel("Nuevo estado:"));
        panel.add(estadoCombo);
        panel.add(new JLabel("Técnico asignado:"));
        panel.add(tecnicoCombo);
        panel.add(new JLabel("Recurso asignado:"));
        panel.add(recursoField);

        int confirm = JOptionPane.showConfirmDialog(this, panel, "Modificar Orden de Trabajo", JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            Tecnico nuevoTecnico = (Tecnico) tecnicoCombo.getSelectedItem();
            if (!nuevoTecnico.equals(ot.getTecnicoAsignado())) {
                if (nuevoTecnico.getNombre().equals(ot.getTecnicoAsignado().getNombre()) ^ nuevoTecnico.getMail().equals(ot.getTecnicoAsignado().getMail())) {
                    JOptionPane.showMessageDialog(this, "Error: Verifique que el nombre y mail del técnico estén correctamente relacionados.");
                    return;
                }
            }

            ot.setPrioridad(prioridadCombo.getSelectedItem().toString());
            ot.setEstado((EstadoOrden) estadoCombo.getSelectedItem());
            ot.setTecnicoAsignado(nuevoTecnico);
            ot.setRecurso(recursoField.getText());
            otDAO.actualizarOT(ot);
            cargarOTs();
            JOptionPane.showMessageDialog(this, "OT actualizada correctamente.");
        }
    }

    private void eliminarOTsCerradas() {
        List<OrdenTrabajo> todas = otDAO.obtenerTodas();
        long cantidad = todas.stream().filter(ot -> ot.getEstado() == EstadoOrden.FINALIZADO || ot.getEstado() == EstadoOrden.CANCELADO).count();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea eliminar las " + cantidad + " OTs FINALIZADAS o CANCELADAS?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            otDAO.eliminarFinalizadasOCanceladas();
            cargarOTs();
            JOptionPane.showMessageDialog(this, "Se eliminaron " + cantidad + " órdenes finalizadas o canceladas.");
        }
    }
}
