package neeapaste;

import java.util.List;
import org.springframework.jdbc.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Class to represent a User.
 */
public class User {

    private String mUsername;
    private String mPassword;

    /**
     * Create a new user
     * @param aUsername
     * @param aPassword
     */
    public User(String aUsername, String aPassword) {
        mUsername = aUsername;
        mPassword = aPassword;
    }

    /**
     * Return true if correct password associated with user
     * @param aPassword The password to validate
     * @return
     */
    public boolean authenticate(String aPassword) {
        return BCrypt.checkpw(aPassword,mPassword);
    }
}
