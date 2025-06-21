package InterfazGrafica;

import DAO.OrdenTrabajoDAO;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionarOTFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filtroEstadoCombo;
    private JComboBox<String> filtroPrioridadCombo;
    private OrdenTrabajoDAO otDAO = new OrdenTrabajoDAO();

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
            "Técnico", "Recurso", "Asignación", "Finalización"
        });

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton enProcesoBtn = new JButton("Marcar como EN PROCESO");
        enProcesoBtn.addActionListener(e -> cambiarEstadoSeleccionado(EstadoOrden.EN_PROCESO));
        bottomPanel.add(enProcesoBtn);

        JButton finalizarBtn = new JButton("Marcar como FINALIZADO");
        finalizarBtn.addActionListener(e -> cambiarEstadoSeleccionado(EstadoOrden.FINALIZADO));
        bottomPanel.add(finalizarBtn);

        JButton cancelarBtn = new JButton("Cancelar OT");
        cancelarBtn.addActionListener(e -> cambiarEstadoSeleccionado(EstadoOrden.CANCELADO));
        bottomPanel.add(cancelarBtn);

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
            tableModel.addRow(new Object[]{
                ot.getNumero(), ot.getNumeroTramite(), ot.getPrioridad(),
                ot.getEstado().name(), ot.getResponsable(),
                ot.getTecnicoAsignado().getNombre(), ot.getRecurso(),
                ot.getFechaAsignacion(), ot.getFechaFinalizacion()
            });
        }
    }

    private void cambiarEstadoSeleccionado(EstadoOrden nuevoEstado) {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una OT primero.");
            return;
        }
        int numeroOT = (int) tableModel.getValueAt(fila, 0);

        String observaciones = JOptionPane.showInputDialog(this, "Observaciones al cambiar estado a " + nuevoEstado.name() + ":");
        if (observaciones != null && !observaciones.trim().isEmpty()) {
            System.out.println("Observación registrada: " + observaciones); // Guardar o procesar según necesidad
        }

        otDAO.actualizarEstado(numeroOT, nuevoEstado.name());
        cargarOTs();
        JOptionPane.showMessageDialog(this, "Estado actualizado a " + nuevoEstado.name());
    }
}
