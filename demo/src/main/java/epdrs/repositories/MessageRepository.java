/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epdrs.repositories;

import epdrs.model.Credential;
import epdrs.model.Message;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author tasos
 */

@Repository
@CrossOrigin(origins = "*")
public interface MessageRepository extends MongoRepository<Message,String> {
    
//     List<Message>  findByReceiverID(String receiverID);

    List<Message> findByReceiverAndSenderOrSenderAndReceiverOrderByDateAsc(Credential receiver,Credential sender,Credential sender2,Credential receiver2);
   
    List <Message> findByReceiverOrSender(Credential receiver,Credential sender);
}
