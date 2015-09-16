package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void insert(String aTitle, String aContent) {
        jdbcTemplate.update("INSERT INTO pastes(title, content, views) VALUES(?, ?, ?)", aTitle, aContent, 0);
    }

    public List<Map<String, Object>> findAllPastes() {
        return jdbcTemplate.queryForList("SELECT * FROM pastes ORDER BY id");
    }

}


