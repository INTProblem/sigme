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
    private JComboBox<String> prioridadCombo;
    private JComboBox<Tecnico> tecnicoCombo;
    private JTextField problemaField, mailtecnicoField, recursoField;

    public GenerarOTFrame() {
        setTitle("Generar OT desde Nota");
        setSize(600, 300);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);

        NotaDAO notaDAO = new NotaDAO();
        OrdenTrabajoDAO otDAO = new OrdenTrabajoDAO();
        TecnicoDAO tecnicoDAO = new TecnicoDAO();

        List<Nota> notas = notaDAO.obtenerTodas();
        List<Tecnico> tecnicos = tecnicoDAO.obtenerTodos();

        notaCombo = new JComboBox<>();
        for (Nota nota : notas) {
            if (nota.isJustificada() && otDAO.obtenerPorNumeroTramite(nota.getId()) == null) {
                notaCombo.addItem(nota);
            }
        }

        prioridadCombo = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        tecnicoCombo = new JComboBox<>();
        for (Tecnico tecnico : tecnicos) {
            tecnicoCombo.addItem(tecnico);
        }

        problemaField = new JTextField();
        mailtecnicoField = new JTextField();
        recursoField = new JTextField();

        mailtecnicoField.setEditable(false);

        add(new JLabel("Seleccionar Nota Justificada:"));
        add(notaCombo);
        add(new JLabel("Prioridad:"));
        add(prioridadCombo);
        add(new JLabel("Técnico asignado:"));
        add(tecnicoCombo);
        add(new JLabel("Mail del Técnico:"));
        add(mailtecnicoField);
        add(new JLabel("Recurso asociado:"));
        add(recursoField);
        add(new JLabel("Problema asociado:"));
        add(problemaField);

        JButton generarBtn = new JButton("Generar OT");
        generarBtn.addActionListener(e -> generarOT());
        add(generarBtn);

        notaCombo.addActionListener(e -> mostrarInfo());
        tecnicoCombo.addActionListener(e -> actualizarMailTecnico());

        setVisible(true);
    }

    private void mostrarInfo() {
        Nota nota = (Nota) notaCombo.getSelectedItem();
        if (nota != null) {
            problemaField.setText(nota.getDescripcion());
        }
    }

    private void actualizarMailTecnico() {
        Tecnico tecnico = (Tecnico) tecnicoCombo.getSelectedItem();
        if (tecnico != null) {
            mailtecnicoField.setText(tecnico.getMail());
        }
    }

    private void generarOT() {
        Nota nota = (Nota) notaCombo.getSelectedItem();
        String prioridad = (String) prioridadCombo.getSelectedItem();
        Tecnico tecnico = (Tecnico) tecnicoCombo.getSelectedItem();
        String recurso = recursoField.getText();

        if (nota == null || prioridad == null || tecnico == null || recurso.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos y seleccionar valores válidos.");
            return;
        }

        OrdenTrabajo ot = new OrdenTrabajo(
            0,
            nota.getId(),
            prioridad,
            EstadoOrden.EVALUACION,
            nota.getFirmante(),
            tecnico,
            recurso,
            nota.getDescripcion(),
            LocalDate.now(),
            null
        );

        OrdenTrabajoDAO dao = new OrdenTrabajoDAO();
        dao.insertarOT(ot);

        JOptionPane.showMessageDialog(this, "OT generada correctamente.");
        dispose();
    }
}
