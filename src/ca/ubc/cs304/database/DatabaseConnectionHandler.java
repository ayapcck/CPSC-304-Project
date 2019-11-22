package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static DatabaseConnectionHandler DBHandlerInstance = null;

	private Connection connection = null;

	public static DatabaseConnectionHandler getDBHandlerInstance() {
		if (DBHandlerInstance == null) {
			DBHandlerInstance = new DatabaseConnectionHandler();
		}
		return DBHandlerInstance;
	}

	private DatabaseConnectionHandler() {
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

	private Reservation getReservation(int confNo) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM RESERVATIONS WHERE CONFNO = ?");
			ps.setInt(1, confNo);
			ResultSet reservations = ps.executeQuery();
			Reservation reservation = Reservation.parseReservationFromResultSet(reservations);
			ps.close();
			return reservation;
		} catch (SQLException e) {
			System.out.println("No reservation found with supplied confNo: " + confNo + "\n" + e.getMessage());
			return null;
		}
	}

	private List<Vehicle> getVehiclesByVTName(String vtName) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM VEHICLE WHERE VTNAME = ?");
			ps.setString(1, vtName);
			ResultSet rs = ps.executeQuery();
			List<Vehicle> vehicles = null; // TODO;
			ps.close();
			return vehicles;
		} catch (SQLException e) {
			System.out.println("No vehicles found with vehicle type: " + vtName + "\n" + e.getMessage());
			return null;
		}
	}

	private Customer getCustomerByDriversLicense(String driversLicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE DRIVERSLICENSE = ?");
			ps.setString(1, driversLicense);
			ResultSet rs = ps.executeQuery();
			Customer customer = Customer.parseCustomerFromResultSet(rs);
			ps.close();
			return customer;
		} catch (SQLException e) {
			System.out.println("No customer found with drivers license: " + driversLicense + "\n" + e.getMessage());
			return null;
		}
	}

	public void rentVehicleWithReservation() {
		try {
			int confNo = 200; // TODO: change
			// TODO Potential bug drivers license and other limited CHARs must be <= specified amount

			Statement selectCustomers = connection.createStatement();

			Reservation reservation = getReservation(confNo);
			assert reservation != null;
			String vtName = reservation.getVtName();

			Customer customer = getCustomerByDriversLicense(reservation.getdLicense());

			List<Vehicle> vehicles = getVehiclesByVTName(vtName);

			// arbitrarily choose the first car of the make since we don't know availability
			// TODO: actually need to choose a vehicle from vehicles list

//            int odometer = vehicle.getInt(7);
//            int cardNo = odometer % 2 + 564979545;

			// have the reservation made at this point
			// confNo Should be unique because confNo is a primary key
//            int rID = confNo / 2;


//            Rental rental = new Rental(rID,
//					vehicle.getString(2),
//					reservation.getdLicense(),
//					reservation.getFromDate(),
//					reservation.getFromTime(),
//					reservation.getToDate(),
//					reservation.getToTime(),
//					odometer,
//					customer.getString(2),
//					cardNo,
//					"02/21",
//					reservation.getConfNo());

			// inserts into database
//            insertIntoRental(rental);
			// TODO: remove car from available?
		} catch (SQLException s) {
			System.out.println("No reservation with that confirmation is found. Please make a new reservation or enter" +
					"a new confirmation number");
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
