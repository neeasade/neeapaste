package neeapaste;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Map a row to a user object.
 */
public class UserRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"),
                        rs.getString("password"));
    }
}
