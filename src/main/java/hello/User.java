package hello;

import java.util.List;
import org.springframework.jdbc.*;

/**
 * Class to represent a User.
 */
public class User {

    private String mUsername;
    private String mPassword;
    private List<Long> aOwnedPastes;

    /**
     * Create a new user
     * @param aUsername
     * @param aPassword
     */
    public User(String aUsername, String aPassword)
    {

    }

    /**
     * Return true if correct password associated with user
     * @param password
     * @return
     */
    public boolean authenticate(String password)
    {
        return false;
    }
}
