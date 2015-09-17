package neeapaste;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implement the RowMapper interface for paste objects.
 */
public class PasteRowMapper implements RowMapper {
	// based off of:
	// http://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Paste lPaste = new Paste();
		lPaste.setContent(rs.getString("content"));
		lPaste.setTitle(rs.getString("title"));
		lPaste.setId(rs.getLong("id"));
		lPaste.setViews(rs.getLong("views"));
		return lPaste;

		// Alternatively - rid yo'self of setters.
		/*
		return new Paste(rs.getLong("id"),
						 rs.getString("title"),
            		 	 rs.getString("content"),
            			 rs.getLong("views"));
        */
	}

}
