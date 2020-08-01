package epdrs.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epdrs.model.Credential;
import epdrs.model.LoginCredential;
import epdrs.model.Token;
import epdrs.repositories.CredentialRepository;
import epdrs.repositories.TokenRepository;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@PostMapping("/user")
	public Token getById(@RequestBody LoginCredential loginCredential) {

		List<Credential> credentials = credentialRepository.findByUsernameAndPassword(loginCredential.getUsername(), loginCredential.getPassword());
		if (credentials.size() == 0) {
			throw new RuntimeException("Invalid Username or Password");
		}
		String alphanumeric = UUID.randomUUID().toString();
		String usersId = credentials.get(0).getId();
		Token token = new Token(alphanumeric, usersId, loginCredential.getUsername(), credentials.get(0).getEnabled());
		tokenRepository.save(token);

		return token;
	}
}
