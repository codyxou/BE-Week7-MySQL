package projects;

import java.sql.Connection;

import projects.dao.DBConnection;

public class projects {

	public static void main(String[] args) {
	
		Connection conn = DBConnection.getConnection();

	}

}
