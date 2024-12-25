package hongik.db;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@SpringBootApplication
public class DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbApplication.class, args);
	}

}

@Component
class DatabaseInitializer {
	private final DataSourceProperties dataSourceProperties;

	public DatabaseInitializer(DataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	@PostConstruct
	public void connectionToDatabase() {
		String url = dataSourceProperties.getUrl();
		String username = dataSourceProperties.getUsername();
		String password = dataSourceProperties.getPassword();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loading success");

			try {
				Connection connection = DriverManager.getConnection(url, username, password);
				System.out.println("connection success");

				Statement stmt = connection.createStatement();
				stmt.execute("set foreign_key_checks = 0");
				stmt.execute("drop table IF EXISTS member");
				stmt.execute("drop table IF EXISTS team");
				stmt.execute("drop table IF EXISTS memberSchedule");
				stmt.execute("drop table IF EXISTS teamSchedule");
				stmt.execute("drop table IF EXISTS teamMember");
				stmt.execute("set foreign_key_checks = 1");

				stmt.close();

				createMemberTable(connection);
				createTeamTable(connection);
				createMemberScheduleTable(connection);
				createTeamScheduleTable(connection);
				createTeamMemberTable(connection);
			} catch (SQLException e) {
				System.out.println("connection fail" + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("driver loading fail" + e.getMessage());
		}
	}

	private static void createMemberTable(Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS member ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "name VARCHAR(100) UNIQUE NOT NULL, "
				+ "password VARCHAR(50) NOT NULL, "
				+ "count INT)";
		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("member table created successful");
		} catch (SQLException e) {
			System.err.println("fail to create member table: " + e.getMessage());
		}
	}

	private static void createTeamTable(Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS team ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "name VARCHAR(100) UNIQUE NOT NULL,"
				+ "password VARCHAR(50) NOT NULL)";
		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("team table created successful");
		} catch (SQLException e) {
			System.err.println("fail to create team table: " + e.getMessage());
		}
	}

	private static void createMemberScheduleTable (Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS memberSchedule ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "title VARCHAR(100),"
				+ "member_id INT NOT NULL,"
				+ "schedule DATE NOT NULL,"
				+ "FOREIGN KEY (member_id) REFERENCES member(id))";

		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("userSchedule table created successfully.");
		} catch (SQLException e) {
			System.err.println("Failed to create userSchedule table: " + e.getMessage());
		}
	}

	private static void createTeamScheduleTable (Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS teamSchedule ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "title VARCHAR(100),"
				+ "team_id INT NOT NULL,"
				+ "schedule DATE NOT NULL,"
				+ "FOREIGN KEY (team_id) REFERENCES team(id))";

		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("teamSchedule table created successfully.");
		} catch (SQLException e) {
			System.err.println("Failed to create teamSchedule table: " + e.getMessage());
		}
	}

	private static void createTeamMemberTable (Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS teamMember ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "team_id INT NOT NULL,"
				+ "member_id INT,"
				+ "FOREIGN KEY (team_id) REFERENCES team(id),"
				+ "FOREIGN KEY (member_id) REFERENCES member(id))";

		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("teamMember table created successfully.");
		} catch (SQLException e) {
			System.err.println("Failed to create teamMember table: " + e.getMessage());
		}
	}
}