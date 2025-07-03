package InterfazGrafica;

import DAO.NotaDAO;
import modelo.Nota;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CargarNotaFrame extends JFrame {
    private JTextField areaField, firmanteField, mailField;
    private JTextArea descripcionArea;

    public CargarNotaFrame() {
        setTitle("Cargar Nota");
        setSize(800, 400);
        setLayout(new GridLayout(6, 2));
        setLocationRelativeTo(null);

        JOptionPane.showMessageDialog(this, "Para cargar una nota debe estar justificada y todos los campos deben estar completos.");

        areaField = new JTextField();
        firmanteField = new JTextField();
        mailField = new JTextField();
        descripcionArea = new JTextArea(3, 20);

        add(new JLabel("Área:")); add(areaField);
        add(new JLabel("Firmante:")); add(firmanteField);
        add(new JLabel("Correo electrónico:")); add(mailField);
        add(new JLabel("Descripción:")); add(new JScrollPane(descripcionArea));

        JButton guardarBtn = new JButton("Guardar Nota");
        guardarBtn.addActionListener(e -> guardarNota());
        add(guardarBtn);

        setVisible(true);
    }

    private void guardarNota() {
        String area = areaField.getText();
        String firmante = firmanteField.getText();
        String mail = mailField.getText();
        String descripcion = descripcionArea.getText();

        if (area.isEmpty() || firmante.isEmpty() || mail.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos para cargar la nota.");
            return;
        }

        if (!mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico ingresado no es válido.");
            return;
        }

        Nota nota = new Nota(
            0,
            descripcion,
            area,
            firmante,
            mail,
            LocalDateTime.now(),
            true
        );

        NotaDAO dao = new NotaDAO();
        dao.insertarNota(nota);
        JOptionPane.showMessageDialog(this, "Nota guardada exitosamente.");
        dispose();
    }
}
