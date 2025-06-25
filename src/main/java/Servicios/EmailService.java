package Servicios;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public static void enviarCorreo(String destinatario, String asunto, String mensaje) {
        // Leemos desde variables de entorno seguras
        String correoEmisor = System.getenv("Juanmaximilianog@gmail.com ");
        String contraseña = System.getenv("1234");

        if (correoEmisor == null || contraseña == null) {
            System.err.println("No se han configurado las variables de entorno para el correo.");
            return;
        }

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoEmisor, contraseña);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(correoEmisor));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject(asunto);
            msg.setText(mensaje);

            Transport.send(msg);
            System.out.println("📨 Correo enviado a: " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


