package com.residencia.comercio.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Value("${spring.mail.port}")
	private String mailPort;
	
	@Value("${spring.mail.username}")
	private String mailUserName;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Autowired
	JavaMailSender emailSender;
	
	public EmailService(JavaMailSender javaMailSender) {
		this.emailSender = javaMailSender;
	}
	
	public void enviarEmailTexto(String destinatarioEmail, String assunto, String mensagemEmail) {
		SimpleMailMessage sMailMessage = new SimpleMailMessage();
		
		sMailMessage.setTo(destinatarioEmail);
		sMailMessage.setSubject(assunto);
		sMailMessage.setText(mensagemEmail);
		
//		Podemos definir o email do remente no arquivo de propriedades ou configurar aqui sempre que quiser
//		Cuidado no momento de usar um servidor real, para setar um remetente válido abaixo
		sMailMessage.setFrom("teste@teste.com");
		
		emailSender.send(sMailMessage);
	}
	
	public void enviarEmailHtml(MultipartFile file, String destinatarioEmail, String assunto, String mensagemEmail) throws MessagingException {
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<h3>Hello World!</h3>";
		
		helper.setText(htmlMsg, true);
		helper.setTo("someone@abc.com");
		helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
		helper.setFrom("abc@gmail.com");
		helper.addAttachment(htmlMsg, file);
		emailSender.send(mimeMessage);
		
	}
	
}
