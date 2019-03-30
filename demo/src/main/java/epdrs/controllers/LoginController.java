package epdrs.controllers;

import epdrs.repositories.CredentialRepository;
import epdrs.repositories.TokenRepository;
import epdrs.model.Token;
import epdrs.model.Credential;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	private TokenRepository tokenRepository;
    
	
	
	@PostMapping("/{username}/{password}")
	public Token getById(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		
		List<Credential> credentials = credentialRepository.findByUsernameAndPassword(username, password);
		if(credentials.size()==0) {
			throw new RuntimeException("Invalid Username or Password");
		}
		String alphanumeric = UUID.randomUUID().toString();
		String usersId = credentials.get(0).getId();
		Token token = new Token(alphanumeric, usersId,username,credentials.get(0).getEnabled());
		tokenRepository.save(token);

		return token;
	}
}
