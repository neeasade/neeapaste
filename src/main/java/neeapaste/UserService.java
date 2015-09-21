package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service for interacting with the users in the database.
 */
@Repository
public class UserService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * Insert a new user into the database
     * @param aUsername
     * @param aPassword
     */
    public void insert(String aUsername, String aPassword) {
        //TODO: trim values to set lengths

        //New user, hash the password.
        //BCryptPasswordEncoder lEncoder = new BCryptPasswordEncoder();
        //String lPassword = lEncoder.encode(aPassword);

        //Insert the new user into the paste_users table.
        jdbcTemplate.update("INSERT INTO paste_users(username, password) VALUES(?, ?)", aUsername, aPassword);
    }

    /**
     * Authenticate a user to do an action.
     * @param aId
     * @param aPassword
     * @return
     */
    public boolean authenticate(String aUserName, String aPassword) {
        return getById(getIdFromUsername(aUserName)).authenticate(aPassword);
    }

    /**
     * Get all paste Id's belonging to a user.
     * @return
     */
    public List<Long> findPastes(String aUsername) {
        Long lId = getIdFromUsername(aUsername);
        //Gather the ID's belong ing to this user.
        List<Map<String,Object>> lPasteRows = jdbcTemplate.queryForList("SELECT * FROM paste_relates WHERE user_id = " + Long.toString(lId));
        List<Long> lPasteIds = new ArrayList<>();

        for (Map lRow : lPasteRows) {
            lPasteIds.add((Long)lRow.get("paste_id"));
        }

        return lPasteIds;
    }

    private User getById(long aId) {
        //validate id
        String lSQL = "SELECT COUNT(*) FROM paste_users";
        Long lUserCount = jdbcTemplate.queryForObject(lSQL, Long.class);
        if ( aId < 1 || aId > lUserCount) {
            return null;
        }

        lSQL = "SELECT * FROM paste_users WHERE id = " + Long.toString(aId);
        User lUser = (User)jdbcTemplate.queryForObject(lSQL, new UserRowMapper());
        return lUser;
    }

    private Long getIdFromUsername(String aUsername) {
        String lSQL = "SELECT id FROM paste_users WHERE username = '" + aUsername + "'";
        return jdbcTemplate.queryForObject(lSQL, Long.class);
    }

    /**
     * Return true if a user exists.
     * @param aUsername
     * @return
     */
    public boolean exists(String aUsername) {
        // Don't do this at home, kiddos
        try {
            jdbcTemplate.queryForObject("SELECT username FROM paste_users WHERE username = '" + aUsername + "'", String.class);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Give ownership of a paste to this user.
     * @param aPasteId
     */
    public boolean ownPaste(String aUsername, Long aPasteId) {
        if(exists(aUsername)) {
            Long lId = getIdFromUsername(aUsername);
            jdbcTemplate.update("INSERT INTO paste_relates(paste_id, user_id) VALUES(?, ?)", lId, aPasteId);
            return true;
        }
        return false;
    }
}
