package hello;

import java.util.List;

/**
 * Created by neeasade on 9/15/15.
 */
public class User {

    private String mUsername;
    private String mPassword;
    private List<Paste> aOwnedPastes;

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
