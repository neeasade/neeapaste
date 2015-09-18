package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
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


    /**
     * Return a paste object with properties set from row.
     * @param aId the id of the paste to return.
     * @return
     */
    public Paste getById(long aId)
    {
        // Validate aId
        String lSQL = "SELECT COUNT(*) FROM pastes";
        Long lPasteCount = (Long)jdbcTemplate.queryForObject(lSQL, Long.class);
        if ( aId < 1 || aId > lPasteCount) {
            return new Paste(-1, "null", "null", -1 );
        }

        lSQL = "SELECT * FROM pastes WHERE id = " + Long.toString(aId);
        Paste lPaste = (Paste)jdbcTemplate.queryForObject(lSQL, new PasteRowMapper());
        // Return lPaste json.
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
        // Validate aId
        String lSQL = "SELECT COUNT(*) FROM pastes";
        Long lPasteCount = (Long)jdbcTemplate.queryForObject(lSQL, Long.class);
        if ( aId < 1 || aId > lPasteCount) {
            return "Paste not found.";
        }

        // Return the property
        Paste lPaste = getById(aId);
        aProperty = aProperty.toLowerCase();

        switch(aProperty) {
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

    /**
     * Return the id of the last paste in the pastes table.
     * @return
     */
    public Long getLastPasteId()
    {
        return jdbcTemplate.queryForObject("SELECT id FROM pastes ORDER BY id DESC LIMIT 1", Long.class);
    }

}


