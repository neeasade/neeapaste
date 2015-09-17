package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * For accessing the database of pastes, and methods that provide different ways of doing so.
 */
@Repository
public class PasteService {

    private JdbcTemplate jdbcTemplate;

    // I am unsure if that is how this works.

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Add a new paste to the database.
     * @param aTitle Title property of new paste.
     * @param aContent Content property of new paste.
     */
    public void insert(String aTitle, String aContent) {
        jdbcTemplate.update("INSERT INTO pastes(title, content, views) VALUES(?, ?, ?)", aTitle, aContent, 0);
    }


    // TODO: change return values on below to not be objects/figure that out.
    // query() instead of queryForList()

    /**
     * Return a paste row with all properties from database - spring formats to json.
     * @param aId
     * @return
     */
    public Object getById(long aId)
    {
        return jdbcTemplate.queryForList("SELECT * FROM pastes WHERE id = " + Long.toString(aId));
    }

    /**
     * Get the property of a paste.
     * @param aID The id of the paste to get.
     * @param aProperty The name of the property to get(column title in database)
     * @return The value of the property for this paste.
     */
    public String getPropertyById(Long aID, String aProperty)
    {
        // TODO: validate id/property and handle that.
        // reminder: increment views property if contenet is called.
        // rowmapper.

        // Columns are all lowercase:
        aProperty = aProperty.toLowerCase();

        // Get the string value without brackets because list, and we just want the value as a string.
        String lReturn = jdbcTemplate.queryForList("SELECT " + aProperty + " FROM pastes WHERE id = " + Long.toString(aID)).get(0).toString();
        lReturn = lReturn.substring(1,lReturn.length() - 1).split("=")[1];
        return lReturn;
    }

    /**
     * Return all the pastes
     * @return
     */
    public List<Map<String, Object>> findAllPastes() {
        return jdbcTemplate.queryForList("SELECT * FROM pastes ORDER BY id");
    }

}


