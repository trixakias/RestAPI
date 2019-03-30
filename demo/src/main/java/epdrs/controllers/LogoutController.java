package epdrs.controllers;

import epdrs.repositories.TokenRepository;
import epdrs.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = "*")
public class LogoutController {

	@Autowired
	private TokenRepository tokenRepository;

	@PostMapping("/user")
	public void getById(@RequestHeader("TOKEN") String tokenAlphanumeric) {
		Token token = tokenRepository.findByAlphanumeric(tokenAlphanumeric);
		if (token == null) {
			throw new RuntimeException("invalid token");
		} else {
			tokenRepository.delete(token);
		}

	}

}
