package epdrs.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 *
 * @author tasos
 */
@Document(collection = "epidrasi")
public class Credential {
    
    @Id
    private String id;
    private String username;
    private String password;
    private String parentId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public Credential(String username, String password, String parentId, String name, String lastName, String email, String phone, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.parentId = parentId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone=phone;
        this.enabled=enabled;
    }
    
    public Credential() {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
	
    
    
}
