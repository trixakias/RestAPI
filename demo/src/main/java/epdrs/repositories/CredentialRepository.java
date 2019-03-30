package epdrs.repositories;

/**
 *
 * @author tasos
 */
import epdrs.model.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;



@Repository
@CrossOrigin(origins = "*")
public interface CredentialRepository extends MongoRepository<Credential,String> {
    
    List<Credential> findByusername(String username);
    List<Credential> findBypassword(String password);
    List <Credential> findByUsernameAndPassword(String username,String password);
    List<Credential> findByParentId(String parentId);

    
}
