package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.ui.TerminalTransactions;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void executeSQLFile(String path) {
		ScriptRunner sr = new ScriptRunner(connection);
		String pathRoot = new File("").getAbsolutePath();
		path = pathRoot + path;
		File file = new File(path);
		try {
			Reader reader = new BufferedReader(new FileReader(file));
			sr.runScript(reader);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void addRequiredTablesAndData() {
		String path = "\\src\\ca\\ubc\\cs304\\database\\AddTablesAndData.sql";
		executeSQLFile(path);
    }

	public void dropAllRequiredTables() {
		String path = "\\src\\ca\\ubc\\cs304\\database\\DropTables.sql";
		executeSQLFile(path);
	}

	public void rentVehicleWithReservation(TerminalTransactions terminalTransactions) {
		// TODO: Main fucntion that handles clerk. Delete when clerk has own class that is called appropriately
		System.out.println("\nEnter confirmation number");
		int confNo = -99999;
		try {
			// TODO Potential bug drivers license and other limited CHARs must be <= specified amount
			confNo = terminalTransactions.readInteger(false);
			String for_rent = "for_rent";
			ArrayList<String> result = new ArrayList<String>();
			Statement stmt = connection.createStatement();
            Statement stmt1 = connection.createStatement();
            Statement stmt2 = connection.createStatement();
            System.out.println("1");
		    ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE CONFNO = " + confNo);
            while (rs.next()) {
                int conf = rs.getInt(1);
                String vtName = rs.getString(2);
                System.out.println(conf + "\t" + vtName);
            }
		    ResultSet vehicle = stmt1.executeQuery("SELECT * FROM VEHICLE WHERE VTNAME = " + rs.getString(2)
                    + " AND STATUS = " + for_rent);
            System.out.println("3");
		    ResultSet customer = stmt2.executeQuery("SELECT * FROM CUSTOMER WHERE DRIVERSLICENSE = " + rs.getString(3));
            System.out.println("4");
		    // arbitrarily choose the first car of the make since we don't know availability
            String vLicense = vehicle.getString(2);
            int odometer = vehicle.getInt(7);
            String cardName = customer.getString(2);
            int cardNo = odometer % 2 + 564979545;
		    // have the reservation made at this confNo. Should be unique because confNo is a primary key
            int rID = confNo / 2;
            Rental rental = new Rental(rID, vLicense, rs.getString(3), rs.getDate(4), rs.getString(5),
                    rs.getDate(6), rs.getString(7), odometer, cardName, cardNo, "02/21",
                    rs.getInt(1));
            // inserts into database
            insertIntoRental(rental);
            // TODO: remove car from available?
            stmt.close();
		} catch (SQLException s) {
			System.out.println("No reservation with that confirmation is found. Please make a new reservation or enter" +
                    "a new confirmation number");
			terminalTransactions.handleClerkInteractions();
		}
	}

	public void insertIntoRental(Rental model) {
        try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO RENTAL VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, model.getrID());
			ps.setString(2, model.getvLicense());
			ps.setString(3, model.getDriversLicense());
			ps.setDate(4, model.getFromDate());
			ps.setString(5, model.getFromTime());
			ps.setDate(6, model.getToDate());
			ps.setString(7, model.getToTime());
			ps.setInt(8, model.getOdometer());
			ps.setString(9, model.getCardName());
			ps.setInt(10, model.getCardNo());
			ps.setString(11, model.getExpDate());
			ps.setInt(12, model.getConfNo());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

    }
//	public void deleteBranch(int branchId) {
//		try {
//			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
//			ps.setInt(1, branchId);
//
//			int rowCount = ps.executeUpdate();
//			if (rowCount == 0) {
//				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
//			}
//
//			connection.commit();
//
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
	
//	public void insertBranch(BranchModel model) {
//		try {
//			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
//			ps.setInt(1, model.getId());
//			ps.setString(2, model.getName());
//			ps.setString(3, model.getAddress());
//			ps.setString(4, model.getCity());
//			if (model.getPhoneNumber() == 0) {
//				ps.setNull(5, java.sql.Types.INTEGER);
//			} else {
//				ps.setInt(5, model.getPhoneNumber());
//			}
//
//			ps.executeUpdate();
//			connection.commit();
//
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
	
	public String[] getAllTables() {
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables");

			while (rs.next()) {
				result.add(rs.getString("table_name"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}	
		
		return result.toArray(new String[result.size()]);
	}
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();	
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
