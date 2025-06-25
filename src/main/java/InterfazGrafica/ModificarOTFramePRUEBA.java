
package InterfazGrafica;

import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ModificarOTFramePRUEBA extends JFrame{

    private JLabel label1;
    private JTextField pruebaField;
    private JComboBox prioridadComboBox;
    
    public ModificarOTFramePRUEBA(DefaultTableModel tableModel) {
        setTitle("Modificar OT");
        setSize(800, 600);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        add(new JLabel("Tecnico asignado")); add(pruebaField);
        prioridadComboBox = new JComboBox<String>();
        prioridadComboBox.addItem("Alta");
        prioridadComboBox.addItem("Media");
        prioridadComboBox.addItem("Baja");
        add(new JLabel("Prioridad:")); add(prioridadComboBox); 
        
        
        
        setVisible(true);
    }
}
