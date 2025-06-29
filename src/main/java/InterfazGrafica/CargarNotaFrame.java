package InterfazGrafica;

import DAO.NotaDAO;
import modelo.Nota;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CargarNotaFrame extends JFrame {
    private JTextField areaField, firmanteField, mailField;
    private JTextArea descripcionArea;
    private JCheckBox justificadaBox;

    public CargarNotaFrame() {
        setTitle("Cargar Nota");
        setSize(800, 400);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(null);

        areaField = new JTextField();
        firmanteField = new JTextField();
        mailField = new JTextField();
        descripcionArea = new JTextArea(3, 20);
        justificadaBox = new JCheckBox("Justificada");

        add(new JLabel("Área:")); add(areaField);
        add(new JLabel("Firmante:")); add(firmanteField);
        add(new JLabel("Correo electrónico:")); add(mailField);
        add(new JLabel("Descripción:")); add(new JScrollPane(descripcionArea));
        add(justificadaBox); add(new JLabel());

        JButton guardarBtn = new JButton("Guardar Nota");
        guardarBtn.addActionListener(e -> guardarNota());
        add(guardarBtn);

        setVisible(true);
    }

    private void guardarNota() {
        Nota nota = new Nota(
            0,
            descripcionArea.getText(),
            areaField.getText(),
            firmanteField.getText(),
            mailField.getText(),
            LocalDateTime.now(),
            justificadaBox.isSelected()
        );
        if(nota.getArea().isEmpty() == true || 
                nota.getFirmante().isEmpty() == true || 
                nota.getMail().isEmpty() == true  
                ){
            JOptionPane.showMessageDialog(this, "Error en los datos cargados. Cargue correctamente los datos.");
        }else if(nota.isJustificada() == false){
            JOptionPane.showMessageDialog(this, "La nota no está justificada; no se guardará en el sistema.");
        }else{
            NotaDAO dao = new NotaDAO();
            dao.insertarNota(nota);
            JOptionPane.showMessageDialog(this, "Nota guardada exitosamente.");
            dispose();
        }
        
    }
}

