package InterfazGrafica;

import DAO.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("SIGME - Iniciar Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        userField = new JTextField();
        passField = new JPasswordField();

        add(new JLabel("Usuario:"));
        add(userField);
        add(new JLabel("Contraseña:"));
        add(passField);

        JButton loginBtn = new JButton("Ingresar");
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        setVisible(true);
    }

    private void login() {
        String usuario = userField.getText();
        String contraseña = new String(passField.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.obtenerUsuario(usuario, contraseña);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + user.getNombreUsuario());
            dispose();
            new MenuFrame(user);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }
    }
}
