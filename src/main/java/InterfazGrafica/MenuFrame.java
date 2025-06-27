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

        JButton generarOTBtn = new JButton("Generar OT desde Nota");
        generarOTBtn.addActionListener(e -> new GenerarOTFrame());
        add(generarOTBtn);

        JButton gestionarOTBtn = new JButton("Gestionar Órdenes de Trabajo");
        gestionarOTBtn.addActionListener(e -> new GestionarOTFrame());
        add(gestionarOTBtn);

        JButton enviarMailBtn = new JButton("Generar Email de OT");
        enviarMailBtn.addActionListener(e -> new EnviarMailOTFrame());
        add(enviarMailBtn);

        setVisible(true);
    }
}
