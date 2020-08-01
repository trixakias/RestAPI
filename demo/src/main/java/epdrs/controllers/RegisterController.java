package epdrs.controllers;

import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epdrs.model.Credential;
import epdrs.model.Token;
import epdrs.repositories.CredentialRepository;
import epdrs.repositories.TokenRepository;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegisterController {

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	TokenRepository tokenRepository;

	@PostMapping("/newUser")
	public void save(@Valid @RequestBody Credential credential) {

		String parentId = credential.getParentId();
		Optional<Credential> result = credentialRepository.findById(parentId);
		if (result.isPresent()) {
			String email = credential.getEmail();
			if (!(credentialRepository.findByEmail(email).isEmpty())) {
				throw new RuntimeException("Email already in use");
			}
			String username = credential.getUsername();
			if (!(credentialRepository.findByusername(username).isEmpty())) {
				throw new RuntimeException("Username already in use");
			}
			credentialRepository.save(credential);
//			sendMail(credential);
		} else {
			throw new RuntimeException("INVALID PARENT ID THROUGH REGISTRATION");
		}
	}

	public void sendMail(Credential credential) {
		try {
			Credential credentialThatHasid = credentialRepository.findByUsernameAndPassword(credential.getUsername(), credential.getPassword()).get(0);
			String alphanumeric = UUID.randomUUID().toString();
			Token token = new Token(alphanumeric, credentialThatHasid.getId(), credentialThatHasid.getUsername(), credential.getEnabled());
			tokenRepository.save(token);

			String fromEmail = ""; // your mail here
			String username = ""; // your username here
			String password = ""; // your password
			String subject = "Account verification!";
			String toEmail = credential.getEmail();
			String temp = "rest.epidrasi.eu/register/enable/" + alphanumeric;
			String message = "Enable your account through this link <a href=" + temp + ">Click here</a>";
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.fallback", "false");

			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(true);

			Message mailmessage = new MimeMessage(mailSession);

			mailmessage.setFrom(new InternetAddress(fromEmail));
			mailmessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			mailmessage.setSubject(subject);
			mailmessage.setContent(message, "text/html");
			Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", username, password);
			transport.sendMessage(mailmessage, mailmessage.getAllRecipients());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	@PostMapping("/enable/{alphanumeric}")
	public String enableAccount(@PathVariable String alphanumeric) {
		Token token = tokenRepository.findByAlphanumeric(alphanumeric);
		Optional<Credential> result = credentialRepository.findById(token.getUsersId());
		Credential user = null;
		if (result.isPresent()) {
			user = result.get();
		} else {
			throw new RuntimeException("No user with this id");
		}
		user.setEnabled(true);
		credentialRepository.save(user);
		return "Your account is now activated";
	}

}
