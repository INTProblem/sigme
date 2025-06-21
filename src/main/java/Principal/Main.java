package Principal;

import javax.swing.SwingUtilities;
import InterfazGrafica.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Iniciar interfaz gráfica de forma segura en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

