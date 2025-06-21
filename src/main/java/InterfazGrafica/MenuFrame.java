package InterfazGrafica;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    public MenuFrame(Usuario user) {
        setTitle("SIGME - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel saludo = new JLabel("Rol: " + user.getRol());
        add(saludo);

        JButton cargarNotaBtn = new JButton("Cargar Nota");
        cargarNotaBtn.addActionListener(e -> new CargarNotaFrame());
        add(cargarNotaBtn);
        
        
        JButton leerNotaBtn = new JButton("Leer Notas");
        leerNotaBtn.addActionListener(e -> new LeerNotaFrame());
        add(leerNotaBtn);

        // Otros botones según el rol podrían ir aquí

        setVisible(true);
    }
}
