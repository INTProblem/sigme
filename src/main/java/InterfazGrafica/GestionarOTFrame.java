package InterfazGrafica;

import DAO.OrdenTrabajoDAO;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GestionarOTFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filtroEstadoCombo;
    private OrdenTrabajoDAO otDAO = new OrdenTrabajoDAO();

    public GestionarOTFrame() {
        setTitle("Gestión de Órdenes de Trabajo");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Filtrar por estado:"));
        filtroEstadoCombo = new JComboBox<>();
        filtroEstadoCombo.addItem("TODOS");
        for (EstadoOrden estado : EstadoOrden.values()) {
            filtroEstadoCombo.addItem(estado.name());
        }
        filtroEstadoCombo.addActionListener(e -> cargarOTs());
        topPanel.add(filtroEstadoCombo);
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
        String filtro = (String) filtroEstadoCombo.getSelectedItem();
        if (!"TODOS".equals(filtro)) {
            ots = ots.stream()
                     .filter(ot -> ot.getEstado().name().equals(filtro))
                     .collect(Collectors.toList());
        }
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
        otDAO.actualizarEstado(numeroOT, nuevoEstado.name());
        cargarOTs();
        JOptionPane.showMessageDialog(this, "Estado actualizado a " + nuevoEstado.name());
    }
}
