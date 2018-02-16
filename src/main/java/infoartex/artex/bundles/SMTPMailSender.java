package infoartex.artex.bundles;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SMTPMailSender {

	private static Properties props = null;
	
	static {
		/*java.security.Security.setProperty("ssl.SocketFactory.provider",
		"smtp.DummySSLSocketFactory");*/
	}
		
	public static void setProperties(boolean tls,String servidor,int puerto, String user){
		
		props = new Properties();
		props.setProperty("mail.smtp.host", servidor);
		props.setProperty("mail.smtp.starttls.enable", Boolean.toString(tls));
		props.setProperty("mail.smtp.port",Integer.toString(puerto));
		props.setProperty("mail.smtp.user", user);
		props.setProperty("mail.smtp.auth", "true");
	}
	
	public static void setProperties(boolean tls,String servidor,String puerto, String user){
		
		props = new Properties();
		props.setProperty("mail.smtp.host", servidor);
		props.setProperty("mail.smtp.starttls.enable", Boolean.toString(tls));
		props.setProperty("mail.smtp.port",puerto);
		props.setProperty("mail.smtp.user", user);
		props.setProperty("mail.smtp.auth", "true");
	}
		
	public static boolean sendMail (final String password, List<String> destinatarios, String asunto, String texto) {
		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(props.getProperty("mail.smtp.user"), password);
				}
			  });
			session.setDebug(true);
			InternetAddress []addresses = new InternetAddress[destinatarios.size()];
			int i = 0;
			for (String dest : destinatarios)
				addresses[i++] = new InternetAddress(dest);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("mail.smtp.user")));
			message.setRecipients(Message.RecipientType.TO, addresses);
			message.setSubject(asunto);
			message.setText(texto);
	
			Transport.send(message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean sendMail(String remitente, String password, List<String> destinatarios, String asunto,String mensaje, File[] attachments){
		try{
			
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(remitente));
			
			for(String destinatario:destinatarios){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}
			
			message.setSubject(asunto);
			
			Multipart multipart = new MimeMultipart();
			
			//texto
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mensaje);

			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			
			Transport t = session.getTransport("smtp");
			t.connect(props.getProperty("mail.smtp.user"),password);
			t.sendMessage(message,message.getAllRecipients());
			t.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
			return false;
		}
		return true;
	}
}
