package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.TimePeriod;
import ca.ubc.cs304.ui.TerminalTransactions;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

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

	public void addRequiredTables() {
		// ScriptRunner sr = new ScriptRunner(connection);
		String pathRoot = new File("").getAbsolutePath();
		String path = "\\src\\ca\\ubc\\cs304\\database\\tables";
		path = pathRoot + path;
		File tableDir = new File(path);
		File[] tables = tableDir.listFiles();
		if (tables != null) {
			for (File file : tables) {
				try {
					Reader reader = new BufferedReader(new FileReader(file));
					// sr.runScript(reader);
				} catch (IOException e) {
					System.out.println(EXCEPTION_TAG + " " + e.getMessage());
				}
			}
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

	public void rentVehicleWithReservation(TerminalTransactions terminalTransactions, int confNo) {
		// TODO: Main fucntion that handles clerk. Delete when clerk has own class that is called appropriately
		try {
			// TODO Potential bug drivers license and other limited CHARs must be <= specified amount
			String for_rent = "for_rent";
			Statement stmt = connection.createStatement();
            Statement stmt1 = connection.createStatement();
            Statement stmt2 = connection.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE CONFNO = " + confNo);
		    String vtName = null;
		    String driversLicense = null;
		    Date fromDate = null;
		    String fromTime = null;
		    Date toDate = null;
		    String toTime = null;

            while (rs.next()) {
                confNo = rs.getInt(1);
                vtName = rs.getString(2);
                driversLicense = rs.getString(3);
                fromDate = rs.getDate(4);
                fromTime = rs.getString(5);
                toDate = rs.getDate(6);
                toTime = rs.getString(7);
            }
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM VEHICLE WHERE VTNAME = ? AND STATUS = 'for_rent'");
            ps.setString(1, vtName);
            ResultSet vehicle = ps.executeQuery();
            String vLicense = null;
            int odometer = 0;
            while (vehicle.next()) {
                vLicense = vehicle.getString(2);
                odometer = vehicle.getInt(7);
                break;
            }
//		    ResultSet vehicle = stmt1.executeQuery("SELECT * FROM VEHICLE WHERE VTName = " + vtName
//                    + " AND STATUS = " + for_rent);
            // ResultSet vehicle = stmt1.executeQuery("SELECT * FROM VEHICLE WHERE VTName = 'truck' AND STATUS = 'for_rent' ");
		    // ResultSet customer = stmt2.executeQuery("SELECT * FROM CUSTOMER WHERE DRIVERSLICENSE = " + rs.getString(3));
            PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE  DRIVERSLICENSE = ?");
            ps1.setString(1, driversLicense);
            ResultSet customer = ps1.executeQuery();
		    // arbitrarily choose the first car of the make since we don't know availability
            String cardName = null;
            while (customer.next()) {
                cardName = customer.getString(2);
            }
            int cardNo = odometer % 2 + 564979545;
		    // have the reservation made at this confNo. Should be unique because confNo is a primary key
            int rID = confNo / 2;
            Rental rental = new Rental(rID, vLicense, driversLicense, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, "02/21",
                    confNo);
            // inserts into database
            insertIntoRental(rental);
            // TODO: display receipt in new UI
            // TODO: remove car from available?
            stmt.close();
            ps.close();
            ps1.close();
		} catch (SQLException s) {
			System.out.println("No reservation with that confirmation is found. Please make a new reservation or enter" +
                    "a new confirmation number");
			terminalTransactions.handleClerkInteractions();
		}
	}

	public void rentVehicleWithNoReservation(TerminalTransactions terminalTransactions) {
		// create new reservation;
		int confNo = (int) (Math.random() * 1000);
		System.out.println("Enter type of vehicle");
		String VtName = terminalTransactions.readLine();
		System.out.println("Enter customer's driver license");
		String driverLicense = terminalTransactions.readLine();
		System.out.println("Enter fromDate");
		String stringDate = terminalTransactions.readLine();
		Date fromDate = Date.valueOf(stringDate);
		System.out.println("Enter from Time");
		String fromTime = terminalTransactions.readLine();
		System.out.println("Enter end date (toDate)");
		String stringToDate = terminalTransactions.readLine();
		Date toDate = Date.valueOf(stringToDate);
		System.out.println("Enter to time");
		String toTime = terminalTransactions.readLine();
		// determine if customer already exists, if not add customer to database to avoid errors
		if (!customerExists(driverLicense)) {
			System.out.println("Enter cellNum of customer");
			String cellNum = terminalTransactions.readLine();
			System.out.println("Enter name of customer");
			String name = terminalTransactions.readLine();
			System.out.println("Enter address of customer");
			String address = terminalTransactions.readLine();
			Customer customer = new Customer(cellNum, name, address, driverLicense);
			insertCustomer(customer);
		}
		TimePeriod timePeriod = new TimePeriod(fromDate, fromTime, toDate, toTime);
		insertTimePeriod(timePeriod);
		Reservations reservations = new Reservations(confNo, VtName, driverLicense, fromDate, fromTime, toDate, toTime);
		insertReservation(reservations);

		rentVehicleWithReservation(terminalTransactions, confNo);
	}

	public boolean customerExists(String driversLicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE DRIVERSLICENSE = ?");
			ps.setString(1, driversLicense);
			ps.executeQuery();
			return true; // didn't crash so customer exists

		} catch (SQLException e) {
			return false;

		}
	}
	/**
	 * Insert rental model into database
	 * @param model:
	 */
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

    public void insertTimePeriod(TimePeriod model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO TIMEPERIOD VALUES (?,?,?,?)");
			ps.setDate(1, model.getFromDate());
			ps.setString(2, model.getFromTime());
			ps.setDate(3, model.getToDate());
			ps.setString(4, model.getToTime());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}

	public void insertCustomer(Customer model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)");
			ps.setString(1, model.getCellNum());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getDriversLicense());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}

	public void insertReservation(Reservations model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?,?,?,?,?,?,?)");
			ps.setInt(1, model.getConfNo());
			ps.setString(2, model.getVtName());
			ps.setString(3, model.getdLicense());
			ps.setDate(4, model.getFromDate());
			ps.setString(5, model.getFromTime());
			ps.setDate(6, model.getToDate());
			ps.setString(7, model.getToTime());

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
	
    }

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
			System.out.println("roll back");
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public int checkVehicleNum(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate ) {
		try {
			// TODO: do we still need the timePeriod parameter?
			PreparedStatement ps = connection.prepareStatement
					("SELECT COUNT(*) AS num FROM VEHICLE V WHERE V.vtName = ? AND V.location = ? AND V.city = ? ");
			ps.setString(1, carType);
			ps.setString(2, location);
			ps.setString(3, city);
//			Date fromDate = new Date(timePeriod.getFromDate().getTimeInMillis());
//			ps.setDate(5, fromDate);
//			Date toDate = new Date(timePeriod.getToDate().getTimeInMillis());
//			ps.setDate(6, toDate);
//			ps.setString(7, branch.getLocation());
//			ps.setString(8, branch.getCity());
			int rs = ps.executeQuery().getInt("num");
			connection.commit();
			ps.close();
			return rs;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return 0;
	}

	public JTable showVehicleDetails(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("SELECT * FROM VEHICLE V WHERE V.vtName = ? AND V.location = ? AND V.city = ? ");
			ps.setString(1, carType);
			ps.setString(2, location);
			ps.setString(3, city);
			ResultSet rs = ps.executeQuery();
			connection.commit();
			ps.close();
			JTable table = new JTable(buildTableModel(rs));
			return table;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return null;
	}

	public static DefaultTableModel buildTableModel(ResultSet rs)
			throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}

}
