package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;

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
     * Return a paste object with properties set from row.
     * @param aId the id of the paste to return.
     * @return
     */
    public Paste getById(long aId)
    {
        String lSQL = "SELECT * FROM pastes WHERE id = " + Long.toString(aId);
        Paste lPaste = (Paste)jdbcTemplate.queryForObject(lSQL, new PasteRowMapper());
        return lPaste;
    }

    /**
     * Increment the view count of a paste.
     * @param aId The id of the paste to increment the view value on.
     */
    private void incrementViewCount(Long aId)
    {
        // Get the viewcount.
        String lViewSQL = "SELECT views FROM pastes WHERE id = " + Long.toString(aId);
        Long lViewCount = (Long)jdbcTemplate.queryForObject(lViewSQL, Long.class);

        // Update the viewcount.
        String lSQL = "UPDATE pastes SET views = " + Long.toString(++lViewCount) + "WHERE id = " + Long.toString(aId);
        jdbcTemplate.update(lSQL);
    }


    /**
     * Get the property of a paste.
     * @param aID The id of the paste to get.
     * @param aProperty The name of the property to get(column title in database)
     * @return The value of the property for this paste.
     */
    public String getPropertyById(Long aId, String aProperty)
    {
        Paste lPaste = getById(aId);
        aProperty = aProperty.toLowerCase();

        // TODO: validate id
        switch(aProperty)
        {
            case "title": return lPaste.getTitle();
            case "content": incrementViewCount(aId); return lPaste.getContent();
            case "id" : return Long.toString(lPaste.getId()); // heh.
            case "views" : return Long.toString(lPaste.getViews());
            default:
                return "Property " + aProperty + " not found.";
        }

    }

    /**
     * Return all the pastes
     * @return
     */
    public List<Map<String, Object>> findAllPastes() {
        return jdbcTemplate.queryForList("SELECT * FROM pastes ORDER BY id");
    }

}


