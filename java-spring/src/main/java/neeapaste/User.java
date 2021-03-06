package neeapaste;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;

/**
 * Class to represent a User.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy="Owner")
    public List<Paste> Pastes;

    /**
     * Become the owner of a paste.
     * @param aPaste
     */
    public void OwnPaste(Paste aPaste)
    {
        this.Pastes.add(aPaste);
        aPaste.setOwner(this);
    }

    /**
     * Create a new user
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Empty/for JPA.
    protected User() { }

    /**
     * Return true if correct password associated with user
     * @param aPassword The password to validate
     * @return
     */
    public boolean authenticate(String aPassword) {
        return BCrypt.checkpw(aPassword,password);
    }
}
