package neeapaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {
        // Delete the tables(reset)
        jdbcTemplate.execute("DROP TABLE pastes IF EXISTS");
        jdbcTemplate.execute("DROP TABLE paste_users IF EXISTS");
        jdbcTemplate.execute("DROP TABLE paste_relates IF EXISTS");

        // Create the tables we will be using
        // Notes:
        // SERIAL is an alias - BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE
        // VARCHAR(255) - String that can be up to 255 chars.
        // TEXT - Text with max length 65,535 characters
        //      consider MEDIUMTEXT - 16,777,215 characters
        jdbcTemplate.execute("CREATE TABLE pastes(" +
                "id SERIAL PRIMARY KEY, title VARCHAR(255), content TEXT, views INT)");

        jdbcTemplate.execute("CREATE TABLE paste_users(" +
                "id SERIAL PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))");

        jdbcTemplate.execute("CREATE TABLE paste_relates(" +
                "paste_id BIGINT NOT NULL, user_id BIGINT NOT NULL," +
                " CONSTRAINT fk_PerPaste FOREIGN KEY (paste_id) REFERENCES pastes(id)," +
                " CONSTRAINT fk_PerUser FOREIGN KEY (user_id) REFERENCES paste_users(id))");
    }
}
