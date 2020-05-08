
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
	
/*Email CLASS SECTION*/
public class Email {
	Properties settings;
	String username, password;

	//email datatype use for GUI
	public Email(String recipient, String subject, String content)
	{
		username = "cs370dummyemail@gmail.com"; //Email will be sent through this dummy account
		password = "strongerpassword";
		
		settings = new Properties();
		settings.put("mail.smtp.auth", "true");
		settings.put("mail.smtp.starttls.enable", "true");
		settings.put("mail.smtp.host", "smtp.gmail.com"); //uses GMAIL
		settings.put("mail.smtp.port", "587");
		
		createSession(recipient, subject, content);
		
	}
	
	//email datatype use for CLI (this one allows outputfile selection)
	 public Email(String recipient, String outputName, String subject, String content)
	 {
		 username = "cs370dummyemail@gmail.com"; //Email will be sent through this dummy account
		 password = "strongerpassword";
			
			settings = new Properties();
			settings.put("mail.smtp.auth", "true");
			settings.put("mail.smtp.starttls.enable", "true");
			settings.put("mail.smtp.host", "smtp.gmail.com"); //uses GMAIL
			settings.put("mail.smtp.port", "587");
			
			//send email report using specified output file
			createSessionGivenFileName(recipient, outputName, subject, content);
	}

	//creates the email session and sends the email with the given output file.
		private void createSessionGivenFileName(String recipient, String outputName, String subject, String content) {
			Session session = Session.getInstance(settings, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
				message.setSubject(subject);
				message.setText(content);

				
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				//insert output file to the body of the email
		        Multipart multipart = new MimeMultipart();
		        
		        messageBodyPart = new MimeBodyPart();
		        String file = outputName;
		        String fileName = outputName;
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);
		        message.setContent(multipart);
		        //send email//
				Transport.send(message);

				System.out.println("Email Sent");
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	 
	 
	 
	 
		//creates the email session and sends the email with the default output file.
	private void createSession(String recipient, String subject, String content) {
		Session session = Session.getInstance(settings, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(content);

			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			//insert output file to the body of the email
	        Multipart multipart = new MimeMultipart();
	        
	        messageBodyPart = new MimeBodyPart();
	        //default output file is "Output.txt"
	        String file = "Output.txt";
	        String fileName = "Output.txt";
	        DataSource source = new FileDataSource(file);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        //send email//
			Transport.send(message);

			System.out.println("Email Sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}