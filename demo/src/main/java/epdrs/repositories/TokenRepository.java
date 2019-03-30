package epdrs.repositories;

import epdrs.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins="*")
public interface TokenRepository extends MongoRepository<Token, String> {
 
	Token findByAlphanumeric(String alphanumeric);
}
