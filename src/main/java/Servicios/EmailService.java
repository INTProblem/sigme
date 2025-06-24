package Servicios;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public void enviarCorreo(String destinatario, String asunto, String mensaje) throws Exception {
        // Leer las credenciales desde las variables de entorno
        String correoEmisor = System.getenv("Juanmaximilianog@gmail.com");
        String contraseña = System.getenv("1234");
        
        System.out.println(contraseña);
        if (correoEmisor == null || contraseña == null) {
            throw new Exception("No se han configurado las variables de entorno para el correo.");
        }

        // Configuración de las propiedades de Gmail
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        // Crear la sesión con las credenciales
        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoEmisor, contraseña);
            }
        });

        // Crear el mensaje
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(correoEmisor));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        msg.setSubject(asunto);
        msg.setText(mensaje);

        // Enviar el correo
        Transport.send(msg);
        System.out.println("Correo enviado exitosamente.");
    }
}
