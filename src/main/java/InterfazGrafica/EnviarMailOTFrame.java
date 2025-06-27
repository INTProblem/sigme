package InterfazGrafica;

import DAO.NotaDAO;
import DAO.OrdenTrabajoDAO;
import modelo.Nota;
import modelo.OrdenTrabajo;
import Servicios.EmailService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EnviarMailOTFrame extends JFrame {
    private JComboBox<OrdenTrabajo> otCombo;
    private JTextField destinatarioField, asuntoField;
    private JTextArea cuerpoArea;
    private JRadioButton tecnicoRadio, firmanteRadio;
    private List<Nota> notas;

    public EnviarMailOTFrame() {
        setTitle("Enviar Email de Orden de Trabajo");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel selectorPanel = new JPanel(new GridLayout(3, 2));
        selectorPanel.add(new JLabel("Seleccionar OT:"));
        otCombo = new JComboBox<>();
        OrdenTrabajoDAO dao = new OrdenTrabajoDAO();
        List<OrdenTrabajo> lista = dao.obtenerTodas();

        NotaDAO notaDAO = new NotaDAO();
        notas = notaDAO.obtenerTodas();

        for (OrdenTrabajo ot : lista) {
            otCombo.addItem(new OrdenTrabajo(ot.getNumero(), ot.getNumeroTramite(), ot.getPrioridad(), ot.getEstado(), ot.getResponsable(), ot.getTecnicoAsignado(), ot.getRecurso(), ot.getProblema(), ot.getFechaAsignacion(), ot.getFechaFinalizacion()) {
                @Override
                public String toString() {
                    return "OT - " + getResponsable();
                }
            });
        }
        selectorPanel.add(otCombo);

        tecnicoRadio = new JRadioButton("Enviar a Técnico");
        firmanteRadio = new JRadioButton("Enviar a Firmante");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(tecnicoRadio);
        grupo.add(firmanteRadio);
        selectorPanel.add(tecnicoRadio);
        selectorPanel.add(firmanteRadio);

        tecnicoRadio.addActionListener(e -> prepararFormulario());
        firmanteRadio.addActionListener(e -> prepararFormulario());

        add(selectorPanel, BorderLayout.NORTH);

        JPanel formularioPanel = new JPanel(new GridLayout(3, 2));
        formularioPanel.add(new JLabel("Correo Destinatario:"));
        destinatarioField = new JTextField();
        formularioPanel.add(destinatarioField);

        formularioPanel.add(new JLabel("Asunto:"));
        asuntoField = new JTextField();
        formularioPanel.add(asuntoField);

        formularioPanel.add(new JLabel("Cuerpo:"));
        cuerpoArea = new JTextArea(5, 40);
        JScrollPane scroll = new JScrollPane(cuerpoArea);
        formularioPanel.add(scroll);

        add(formularioPanel, BorderLayout.CENTER);

        JButton enviarBtn = new JButton("Enviar");
        enviarBtn.addActionListener(e -> enviarCorreo());
        add(enviarBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void prepararFormulario() {
        OrdenTrabajo ot = (OrdenTrabajo) otCombo.getSelectedItem();
        if (ot == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una OT.");
            return;
        }

        if (tecnicoRadio.isSelected()) {
            destinatarioField.setText(ot.getTecnicoAsignado().getMail());
        } else if (firmanteRadio.isSelected()) {
            for (Nota nota : notas) {
                if (nota.getId() == ot.getNumeroTramite()) {
                    destinatarioField.setText(nota.getMail());
                    return;
                }
            }
            destinatarioField.setText(ot.getResponsable());
        }
    }

    private void enviarCorreo() {
        String destinatario = destinatarioField.getText().trim();
        String asunto = asuntoField.getText().trim();
        String cuerpo = cuerpoArea.getText().trim();

        if (destinatario.isEmpty() || asunto.isEmpty() || cuerpo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return;
        }

        EmailService.enviarCorreo(destinatario, asunto, cuerpo);
        JOptionPane.showMessageDialog(this, "Correo enviado exitosamente.");
        dispose();
    }
}
