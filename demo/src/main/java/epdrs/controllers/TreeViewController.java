/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epdrs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epdrs.model.Credential;
import epdrs.model.Token;
import epdrs.repositories.CredentialRepository;
import epdrs.repositories.TokenRepository;

@RestController
@RequestMapping("/tree")
@CrossOrigin(origins = "*")
public class TreeViewController {

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@GetMapping("/childrenOfUser/{userId}")
	public List<Credential> returnChildern(@PathVariable("userId") String userId, @RequestHeader("TOKEN") String tokenAlphanumeric) throws RuntimeException {
		validateToken(tokenAlphanumeric);
		return credentialRepository.findByParentId(userId);
	}

	@GetMapping("/getNumberOfTotalFollowers/{usersID}")
	public int numberOfTotalFollowers(@PathVariable("usersID") String usersID, @RequestHeader("TOKEN") String tokenAlphanumeric) throws RuntimeException {
		validateToken(tokenAlphanumeric);
		int count = 0;
		List<Credential> children = credentialRepository.findByParentId(usersID);
		return getNumberOfFollowers(children, count);
	}

	public int getNumberOfFollowers(List<Credential> children, int count) {
		if (!(children.size() == 0)) {
			for (Credential child : children) {
				count = count + 1;
				List<Credential> grandChildren = credentialRepository.findByParentId(child.getId());
				getNumberOfFollowers(grandChildren, count);
			}
		}
		return count;
	}

	public void validateToken(String tokenAlphanumeric) {
		Token token = tokenRepository.findByAlphanumeric(tokenAlphanumeric);
		if (token == null) {
			throw new RuntimeException("invalid token");
		}
	}

}
