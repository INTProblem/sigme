package InterfazGrafica;

import DAO.*;
import modelo.*;
import Servicios.EmailService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class GenerarOTFrame extends JFrame {
    private JComboBox<Nota> notaCombo;
    private JTextField prioridadField, problemaField, tecnicoField, mailtecnicoField;

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
        tecnicoField = new JTextField();
        mailtecnicoField = new JTextField();

        add(new JLabel("Seleccionar Nota Justificada:"));
        add(notaCombo);
        add(new JLabel("Prioridad (Alta/Media/Baja):"));
        add(prioridadField);
        add(new JLabel("Técnico asignado:")); 
        add(tecnicoField);
        add(new JLabel("Mail del Técnico:")); 
        add(mailtecnicoField);
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
        if (nota != null) {
            problemaField.setText(nota.getDescripcion());
        }
    }

    private void generarOT() {
        Nota nota = (Nota) notaCombo.getSelectedItem();
        if (nota == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota válida.");
            return;
        }

        String tecnicoNombre = tecnicoField.getText();
        String tecnicoEmail = mailtecnicoField.getText();

        if (tecnicoNombre.isBlank() || tecnicoEmail.isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete el nombre y correo del técnico.");
            return;
        }

        Tecnico tecnico = new Tecnico(tecnicoNombre, tecnicoEmail);

        OrdenTrabajo ot = new OrdenTrabajo(
            0,
            nota.getId(),
            prioridadField.getText(),
            EstadoOrden.EVALUACION,
            nota.getFirmante(),
            tecnico,
            problemaField.getText(),
            LocalDate.now(),
            null
        );

        OrdenTrabajoDAO dao = new OrdenTrabajoDAO();
        dao.insertarOT(ot);

        // Enviar correo al técnico
        String asunto = "Nueva Orden de Trabajo asignada";
        String mensaje = "Hola " + tecnico.getNombre() + ",\n\nSe te ha asignado una nueva OT correspondiente al trámite Nº " + nota.getId() + ".";
        EmailService.enviarCorreo(tecnico.getMail(), asunto, mensaje);

        JOptionPane.showMessageDialog(this, "OT generada correctamente.\nCorreo enviado a " + tecnico.getMail());
        dispose();
    }
}
