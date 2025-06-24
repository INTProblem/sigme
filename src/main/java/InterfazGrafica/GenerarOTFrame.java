package InterfazGrafica;
import DAO.*;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class GenerarOTFrame extends JFrame {
    private JComboBox<Nota> notaCombo;
    private JTextField prioridadField, problemaField;

    public GenerarOTFrame() {
        setTitle("Generar OT desde Nota");
        setSize(600, 300);
        setLayout(new GridLayout(8, 2));
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
        problemaField = new JTextField();

        add(new JLabel("Seleccionar Nota Justificada:"));
        add(notaCombo);
        add(new JLabel("Prioridad (Alta/Media/Baja):"));
        add(prioridadField);
        add(new JLabel("Problema asociado:"));
        add(problemaField);

        JButton generarBtn = new JButton("Generar OT");
        generarBtn.addActionListener(e -> generarOT());
        add(generarBtn);
        notaCombo.addActionListener(e -> mostrarInfo());
        setVisible(true);
    }

    private void mostrarInfo(){
        Nota nota = (Nota) notaCombo.getSelectedItem();
        problemaField.setText(nota.getDescripcion());
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
            problemaField.getText(),
            LocalDate.now(),
            null
        );

        OrdenTrabajoDAO dao = new OrdenTrabajoDAO();
        dao.insertarOT(ot);
        JOptionPane.showMessageDialog(this, "OT generada correctamente.");
        dispose();
    }
}