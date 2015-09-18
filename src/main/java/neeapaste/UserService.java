package neeapaste;

import org.apache.catalina.users.AbstractUser;
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

    // I am unsure if that is how this works.

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * Insert a new user into the database
     * @param aUsername
     * @param aPassword
     */
    public void insert(String aUsername, String aPassword)
    {
        //TODO: trim values to set lengths

        //New user, hash the password.
        BCryptPasswordEncoder lEncoder = new BCryptPasswordEncoder();
        String lPassword = lEncoder.encode(aPassword);

        //Insert the new user into the paste_users table.
        jdbcTemplate.update("INSERT INTO paste_users(username, password) VALUES(?, ?)", aUsername, lPassword);
    }

    /**
     * Authenticate a user to do an action.
     * @param aId
     * @param aPassword
     * @return
     */
    public boolean authenticate(String aUserName, String aPassword)
    {
        return getById(getIdFromUsername(aUserName)).authenticate(aPassword);
    }

    /**
     * Get all pastes belonging to a user.
     * @return
     */
    public List<Map<String, Object>> findAllPastes(Long aId) {
        // form: SELECT * FROM pastes WHERE id IN (1,2,3)
        //Gather the ID's belong ing to this user.
        List lPasteRows = jdbcTemplate.queryForList("SELECT paste_id FROM paste_relates WHERE user_id = " + Long.toString(aId));
        List<Long> lPasteIds = new ArrayList<>();
        // TODO
        /*
        for (Map m : lPasteRows)
        {
            Long asdf = (Long)m.get("paste_id");
            String asdfasdf = "";
        }
        */
        return jdbcTemplate.queryForList("SELECT id FROM pastes ORDER BY id");
    }

    private User getById(long aId)
    {
        //validate id
        String lSQL = "SELECT COUNT(*) FROM pastes";
        Long lUserCount = jdbcTemplate.queryForObject(lSQL, Long.class);
        if ( aId < 1 || aId > lUserCount) {
            return null;
        }

        lSQL = "SELECT * FROM pastes WHERE id = " + Long.toString(aId);
        User lUser = (User)jdbcTemplate.queryForObject(lSQL, new UserRowMapper());
        return lUser;
    }

    private Long getIdFromUsername(String aUsername)
    {
        String lSQL = "SELECT id FROM paste_users WHERE username = " + aUsername;
        return jdbcTemplate.queryForObject(lSQL, Long.class);
    }

    public void setPasteOwner(Long aUserId, Long aPasteId)
    {
        //insert a relation into the pastes_relate table
        jdbcTemplate.update("INSERT INTO paste_relates(paste_id, user_id) VALUES(?, ?)", Long.toString(aPasteId), Long.toString(aUserId));
    }

    /**
     * Return true if a user exists.
     * @param aUsername
     * @return
     */
    public boolean exists(String aUsername)
    {
        // TODO
        return false;
    }
}
