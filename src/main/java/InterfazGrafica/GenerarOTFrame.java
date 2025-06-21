package InterfazGrafica;
import DAO.*;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class GenerarOTFrame extends JFrame {
    private JComboBox<Nota> notaCombo;
    private JTextField prioridadField, recursoField;

    public GenerarOTFrame() {
        setTitle("Generar OT desde Nota");
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        NotaDAO notaDAO = new NotaDAO();
        List<Nota> notas = notaDAO.obtenerTodas();

        notaCombo = new JComboBox<>();
        for (Nota nota : notas) {
            if (nota.isJustificada()) {
                notaCombo.addItem(nota);
            }
        }

        prioridadField = new JTextField();
        recursoField = new JTextField();

        add(new JLabel("Seleccionar Nota Justificada:"));
        add(notaCombo);
        add(new JLabel("Prioridad (Alta/Media/Baja):"));
        add(prioridadField);
        add(new JLabel("Recurso asociado:"));
        add(recursoField);

        JButton generarBtn = new JButton("Generar OT");
        generarBtn.addActionListener(e -> generarOT());
        add(generarBtn);

        setVisible(true);
    }

    private void generarOT() {
        Nota nota = (Nota) notaCombo.getSelectedItem();
        if (nota == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota válida.");
            return;
        }

        OrdenTrabajo ot = new OrdenTrabajo(
            0,
            nota.getId(),
            prioridadField.getText(),
            EstadoOrden.EVALUACION,
            nota.getFirmante(),
            new Tecnico(nota.getTecnicoAsignado()),
            recursoField.getText(),
            LocalDate.now(),
            null
        );

        OrdenTrabajoDAO dao = new OrdenTrabajoDAO();
        dao.insertarOT(ot);
        JOptionPane.showMessageDialog(this, "OT generada correctamente.");
        dispose();
    }
}