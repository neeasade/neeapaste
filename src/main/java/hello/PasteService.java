package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * For accessing the database of pastes.
 */
@Repository
public class PasteService {

    private JdbcTemplate jdbcTemplate;

    // I am unsure if that is how this works.

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void insert(String aTitle, String aContent) {
        jdbcTemplate.update("INSERT INTO pastes(title, content, views) VALUES(?, ?, ?)", aTitle, aContent, 0);
    }

    public Object getById(long aId)
    {
        return jdbcTemplate.queryForList("SELECT * FROM pastes where id = " + Long.toString(aId));
    }

    /*
    public List<Map<String, Object>> getPropertyById(Long aID, String aProperty)
    {
        return jdbcTemplate.query("SELECT " + aID.toString() " FROM Paste");

    }
    */

    public List<Map<String, Object>> findAllPastes() {
        return jdbcTemplate.queryForList("SELECT * FROM pastes ORDER BY id");
    }

}


