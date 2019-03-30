package epdrs.controllers;

import epdrs.repositories.CredentialRepository;
import epdrs.repositories.MessageRepository;
import epdrs.repositories.TokenRepository;
import epdrs.model.Credential;
import epdrs.model.Message;
import epdrs.model.MessageFromFrontend;
import epdrs.model.Token;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/saveMessages")
@CrossOrigin(origins = "*")
public class MessagesController {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private CredentialRepository credentialRepository;

	@PostMapping("/message")
	public void saveMessages(@RequestBody List<MessageFromFrontend> messages,
			@RequestHeader("TOKEN") String tokenAlphanumeric) throws RuntimeException {
		validateToken(tokenAlphanumeric);
		sendMessages(messages);

	}

	public void sendMessages(List<MessageFromFrontend> messages) {
		for (MessageFromFrontend mnm : messages) {

			String senderID = mnm.getSenderID();
			Credential sender = getCredentialFromId(senderID);

			String receiverID = mnm.getReceiverID();
			Credential receiver = getCredentialFromId(receiverID);

			String text = mnm.getText();

			Message newMessage = new Message(sender, receiver, text);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
			newMessage.setDate(strDate);
			messageRepository.save(newMessage);
		}
	}



	@GetMapping("/getChatBetween/{receiverID}/{senderID}")
	public List<Message> getChat(@PathVariable String receiverID, @PathVariable String senderID,
			@RequestHeader("TOKEN") String tokenAlphanumeric) throws RuntimeException {
			validateToken(tokenAlphanumeric);
			Credential receiver = getCredentialFromId(receiverID);
			Credential sender = getCredentialFromId(senderID);
			return messageRepository.findByReceiverAndSenderOrSenderAndReceiverOrderByDateAsc(receiver, sender,
					receiver, sender);
	}

	
	@GetMapping("/getUsersWithActiveChat/{userId}")
	public List<Credential> getChatConversationsList(@PathVariable String userId,
			@RequestHeader("TOKEN") String tokenAlphanumeric) throws RuntimeException {
		validateToken(tokenAlphanumeric);
		Credential user = getCredentialFromId(userId);
		List<Message> chat = messageRepository.findByReceiverOrSender(user, user);
		List<String> ChatNamesList = new ArrayList<String>();
		List<Credential> credentialChatList = new ArrayList<Credential>();
		for (Message msg : chat) {
			String receiverId = msg.getReceiver().getId();
			String senderId = msg.getSender().getId();
			String name = null;
			String idOfUserChatname = null;
			if (receiverId.equals(userId)) {
				name = msg.getSender().getUsername();
				idOfUserChatname = senderId;
			}
			if (senderId.equals(userId)) {
				name = msg.getReceiver().getUsername();
				idOfUserChatname = receiverId;
			}
			if (!(ChatNamesList.contains(name))) {
				ChatNamesList.add(name);
				Credential chatCredential = getCredentialFromId(idOfUserChatname);
				credentialChatList.add(chatCredential);
			}
		}
		return credentialChatList;
	}

	
	public Credential getCredentialFromId(String ID) {
		Optional<Credential> result = credentialRepository.findById(ID);
		Credential credential = null;
		if (result.isPresent()) {
			credential = result.get();
			return credential;
		} else {
			throw new RuntimeException("No user with this id");
		}
	}

	
	public void validateToken(String tokenAlphanumeric) {
		Token token = tokenRepository.findByAlphanumeric(tokenAlphanumeric);
		if (token == null) {
			throw new RuntimeException("invalid token");
		}
	}

}
