package neeapaste;

import java.util.List;
import org.springframework.jdbc.*;

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
    public User(String aUsername, String aPassword)
    {
        mUsername = aUsername;
        mPassword = aPassword;
    }

    /**
     * Return true if correct password associated with user
     * @param password
     * @return
     */
    public boolean authenticate(String aPassword)
    {
        // Doing bad things.
        return (aPassword == mPassword);
    }
}
