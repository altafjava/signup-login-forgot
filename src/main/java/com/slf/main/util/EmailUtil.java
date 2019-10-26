package com.slf.main.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	private static final Logger LOGGER = LogManager.getLogger(EmailUtil.class);

	@Value("${fromEmail}")
	private String fromEmail;
	@Value("${fromPassword}")
	private String fromPassword;

	Properties properties = new Properties();
	{
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
	}

	public boolean sendMail(String toEmail, String subject, String body) {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, fromPassword);
			}
		};
		Session session = Session.getInstance(properties, authenticator);
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.addHeader("Content-Type", "text/html; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
			mimeMessage.setFrom(new InternetAddress(fromEmail, "GroupBima OTP"));
			mimeMessage.setReplyTo(InternetAddress.parse(fromEmail, false));
			mimeMessage.setSubject(subject, "UTF-8");
			mimeMessage.setText(body, "UTF-8");
			mimeMessage.setSentDate(new Date());
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(mimeMessage);
			LOGGER.info("OTP Successfully Sent to Email " + toEmail);
			return true;
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOGGER.error("OTP sent fail due to : " + e);
		}
		return false;
	}
}
