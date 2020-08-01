package epdrs.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author tasos
 */
import epdrs.model.Credential;

@Repository
@CrossOrigin(origins = "*")
public interface CredentialRepository extends MongoRepository<Credential, String> {

	List<Credential> findByusername(String username);

	List<Credential> findBypassword(String password);

	List<Credential> findByUsernameAndPassword(String username, String password);

	List<Credential> findByParentId(String parentId);

	List<Credential> findByEmail(String email);

}
