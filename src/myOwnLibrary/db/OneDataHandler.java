package myOwnLibrary.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OneDataHandler extends DataHandler{
	public void processRow(ResultSet rs) throws SQLException {
		addRecord(rs.getObject(1));
	}
}
