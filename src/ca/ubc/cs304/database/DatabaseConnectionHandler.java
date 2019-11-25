package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.TimePeriod;
import javafx.util.Pair;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

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

	public Rental rentVehicleWithReservation(int confirmationNumber, String cardName, int cardNumber) {
		Reservation reservation = getReservation(confirmationNumber);
		assert reservation != null;
		String vtName = reservation.getVtName();
		System.out.println(vtName);
		List<ForRent> vehicles = getVehiclesByVTName(vtName);
		assert vehicles != null;
		// arbitrarily choose the first car of the make since we don't know availability
		ForRent forRent = null;
		for (ForRent f: vehicles) {
			if (f.getLocation().equals(reservation.getLocation()) && f.getCity().equals(reservation.getCity())) {
				forRent = f;
			}
		}
		if (forRent == null) {
			System.out.println("No vehicles of type " + reservation.getVtName() + " at " + reservation.getLocation() + " " + reservation.getCity());
			return null;
		}
		// have the reservation made at this confNo. Should be unique because confNo is a primary key
		int rID = confirmationNumber + 1;
		Rental rental = new Rental(rID,
				forRent.getvLicense(),
				reservation.getdLicense(),
				reservation.getFromDate(),
				reservation.getFromTime(),
				reservation.getToDate(),
				reservation.getToTime(),
				forRent.getOdometer(),
				cardName,
				cardNumber,
				"02/21",
				confirmationNumber);
		// inserts into database
		insertIntoRental(rental);
		updateStatus( "notavailable", forRent.getvLicense());
		return rental;
	}

	private Reservation getReservation(int confNo) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM RESERVATIONS WHERE CONFNO = ?");
			ps.setInt(1, confNo);
			ResultSet reservations = ps.executeQuery();
			Reservation reservation = Reservation.createReservationModel(reservations);
			ps.close();
			return reservation;
		} catch (SQLException e) {
			System.out.println("No reservation found with supplied confNo: " + confNo + "\n" + e.getMessage());
			return null;
		}
	}

	private List<ForRent> getVehiclesByVTName(String vtName) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM FORRENT WHERE VTNAME = ? AND STATUS='available'");
			ps.setString(1, vtName);
			ResultSet rs = ps.executeQuery();

			List<ForRent> vehicles = ForRent.createForRentModel(rs);
			ps.close();
			if (vehicles.size() == 0) {
				throw new SQLException();
			}
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

	public Rental rentVehicleWithNoReservation(Reservation reservation, Branch branch,
											 String cardName, int cardNumber) {
		TimePeriod timePeriod = reservation.getTimePeriod();
		insertTimePeriod(timePeriod);
		insertReservation(reservation, branch);
		return rentVehicleWithReservation(reservation.getConfNo(), cardName, cardNumber);
	}

	public Pair<Return, RentalCost> returnVehicle(int rId) throws Exception{
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM RENTAL WHERE  RID = ?");
            ps.setInt(1, rId);
            ResultSet rental = ps.executeQuery();
            String vLicense = null;
            String toDate = null;
            String fromDate = null;
            String toTime = null;
            if (rental.next()) {
                vLicense = rental.getString(2);
                toDate = rental.getString(6);
                toTime = rental.getString(7);
                fromDate = rental.getString(4);
            }

            if (!rentedVehicle(vLicense)) {
                throw new Exception("This vehicle was not rented");
            }

            PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM FORRENT WHERE  VLICENSE = ?");
            ps1.setString(1, vLicense);
            ResultSet forRent = ps1.executeQuery();
            int odometer = -99;
            String vtName = null;
            if (forRent.next()) {
                odometer = forRent.getInt(7);
                vtName = forRent.getString(9);
            }

            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM VEHICLETYPE WHERE  VTNAME = ?");
            ps2.setString(1, vtName);
            ResultSet vType = ps2.executeQuery();
            VehicleType vehicleType = createVehicleTypeModel(vType);

            // get value
            assert fromDate != null;
            RentalCost rentalCost = returnValue(vehicleType, fromDate, toDate);
            int value = rentalCost.getCalculatedCost();
            Return ret = new Return(rId, toDate, odometer, 1, value);
            insertIntoReturn(ret);
			updateStatus( "available", vLicense);
            return new Pair<Return, RentalCost>(ret, rentalCost);
        } catch (SQLException e) {
            System.out.println("No rental with that id exists");
            return null;
        }
    }

    public RentalCost returnValue(VehicleType vehicleType, String fromDateString, String toDateString) {
		Date fromDate = Date.valueOf(fromDateString);
		Date toDate = Date.valueOf(toDateString);
	    long currTime  = toDate.getTime();
	    long startTime = fromDate.getTime();
        long diff = currTime - startTime;
        long hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);

		long days = Math.floorDiv(hours, 24);

        long weeks = Math.floorDiv(days, 7);
        long daysLeft = days - (weeks * 7);
        long hoursLeft = hours - (days * 24);

        return new RentalCost(vehicleType, weeks, daysLeft, hoursLeft);

    }
    public VehicleType createVehicleTypeModel(ResultSet rs) throws SQLException {
	    String vtName = null;
        String features = null;
        int weeklyRate = -99;
        int dailyRate = -99;
        int hourlyRate = -99;
        int weeklyInsuranceRate = -99;
        int dailyInsuranceRate = -99;
        int hourlyInsuranceRate = -99;
        int kmRate = -99;
	    while (rs.next()) {
	        vtName = rs.getString(1);
	        features = rs.getString(2);
	        weeklyRate = rs.getInt(3);
	        dailyRate = rs.getInt(4);
	        hourlyRate = rs.getInt(5);
	        weeklyInsuranceRate = rs.getInt(6);
	        dailyInsuranceRate = rs.getInt(7);
	        hourlyInsuranceRate = rs.getInt(8);
	        kmRate = rs.getInt(9);
        }
        return new VehicleType(vtName, features, weeklyRate, dailyRate, hourlyRate, weeklyInsuranceRate,
                              dailyInsuranceRate, hourlyInsuranceRate, kmRate);
    }


    public boolean rentedVehicle(String vLicense) {
        try {
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT * FROM FORRENT WHERE VLICENSE = ? AND STATUS = 'notavailable'");
            ps.setString(1, vLicense);
            ps.executeQuery();
            return true; // didn't crash so vehicle with vLicense that is rented exists

        } catch (SQLException e) {
            return false;

        }
    }

	public boolean customerExists(String driversLicense) {
		try {
			// TODO: changed customer exists
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(DISTINCT cellNum) AS num FROM CUSTOMER");
			int cusNum = rs.getInt("num");
			if (cusNum > 0) return true;
			else return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public ForRent createForRentModel(ResultSet rs) throws SQLException {
		int vId = -99;
		String vLicense = null;
		String make = null;
		String model = null;
		int year = -99;
		String color = null;
		int odometer = -99;
		String status = null;
		String vtName = null;
		String location = null;
		String city = null;
		while (rs.next()) {
			vId = rs.getInt(1);
			vLicense = rs.getString(2);
			make = rs.getString(3);
			model = rs.getString(4);
			year = rs.getInt(5);
			color = rs.getString(6);
			odometer = rs.getInt(7);
			status = rs.getString(8);
			vtName = rs.getString(9);
			location = rs.getString(10);
			city = rs.getString(11);
			break;
		}
        return new ForRent(vId, vLicense, make, model, year, color, odometer, status, vtName, location, city);
	}

	// tuples are location, city, type of car, number of that car that were rented.
	// Each element in the list corresponds to such a tuple
	public JTable rentalsAtEachLocationByVT(String date) {
	    try {
            PreparedStatement ps = connection.prepareStatement
                    ("select distinct ForRent.location, ForRent.city, ForRent.vtname, COUNT(ForRent.vtname) as num\n" +
                            "from Rental, ForRent\n" +
                            "where Rental.fromDate = ? AND Rental.VLicense = ForRent.vLicense\n" +
                            "group by ForRent.location, ForRent.city, ForRent.vtname");
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			return table;
        } catch (SQLException e) {
            System.out.println("No rentals found on queried date");
            return null; // either we can return an empty rs or throw the exception or just return null;
        }
    }


    // total rentals for whole company on specified date
	public int totalRentalsWholeCompany(String date) {
		int totalRentals = -99;
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct COUNT(Rental.VLicense) as numRentals\n" +
							"from Rental\n" +
							"where Rental.fromDate = ?");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				totalRentals = rs.getInt(1);
			}
			return totalRentals;
		} catch (SQLException e) {
			System.out.println("No rentals found on queried date");
			return totalRentals;
		}
	}

    // tuples are location, city, type of car, number of that car that were rented.
    public JTable totalNumRentalsEachBranch(String date) {
        try {
        	String query = "SELECT DISTINCT ForRent.location, ForRent.city, COUNT(Rental.VLicense) AS numRentals " +
					"FROM Rental, ForRent " +
					"WHERE Rental.fromDate = ? AND Rental.VLicense = ForRent.vLicense " +
					"GROUP BY ForRent.location, ForRent.city";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
        } catch (SQLException e) {
            System.out.println("No rentals found on queried date");
            return null; // either we can return an empty rs or throw the exception or just return null;
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
			ps.setString(4, model.getFromDate());
			ps.setString(5, model.getFromTime());
			ps.setString(6, model.getToDate());
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

    public void insertIntoReturn(Return model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RETURN VALUES (?,?,?,?,?)");
            ps.setInt(1, model.getrID());
            ps.setString(2, model.getReturnDate());
            ps.setInt(3, model.getOdometer());
            ps.setBoolean(4, model.getFullTank()==1);
            ps.setInt(5, model.getValue());

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
			ps.setString(1, model.getFromDate());
			ps.setString(2, model.getFromTime());
			ps.setString(3, model.getToDate());
			ps.setString(4, model.getToTime());
			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}

	public void insertCustomer(Customer customer) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)");
			ps.setString(1, customer.getCellNum());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getAddress());
			ps.setString(4, customer.getDriversLicense());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}

	public boolean vehicleExist(String vtName, String location, String city) {
		if (checkVehicleNum(vtName, location, city) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean insertReservation(Reservation reservation, Branch branch) {
		try {
			if (vehicleExist(reservation.getVtName(), branch.getLocation(), branch.getCity())) {
				TimePeriod resTimePeriod = reservation.getTimePeriod();
				if (!getTimePeriod(resTimePeriod)) {
					insertTimePeriod(resTimePeriod);
				}
				PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?,?,?,?,?,?,?,?,?)");
				System.out.println("confNo: " + reservation.getConfNo());
				System.out.println("vtName: " +reservation.getVtName());
				System.out.println("driver License: " + reservation.getdLicense());
				System.out.println("FromDate: + " + resTimePeriod.getFromDate());
				System.out.println("fromTime: " + resTimePeriod.getFromTime());
				System.out.println("To date: " +resTimePeriod.getToDate());
				System.out.println("To time: " +resTimePeriod.getToTime());
				System.out.println("Location: " + reservation.getLocation());
				System.out.println("City: " + reservation.getCity());
				ps.setInt(1, reservation.getConfNo());
				ps.setString(2, reservation.getVtName());
				ps.setString(3, reservation.getdLicense());
				ps.setString(4, resTimePeriod.getFromDate());
				ps.setString(5, resTimePeriod.getFromTime());
				ps.setString(6, resTimePeriod.getToDate());
				ps.setString(7, resTimePeriod.getToTime());
				ps.setString(8, reservation.getLocation());
				ps.setString(9, reservation.getCity());
				ps.executeQuery();
				connection.commit();
				ps.close();
				return true;
			} else return false;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return false;
	}

	public boolean getTimePeriod(TimePeriod model) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM TIMEPERIOD WHERE FROMDATE = ? AND FROMTIME = ? AND TODATE = ? AND TOTIME = ?");
			ps.setString(1, model.getFromDate());
			ps.setString(2, model.getFromTime());
			ps.setString(3, model.getToDate());
			ps.setString(4, model.getToTime());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				connection.commit();
				ps.close();
				return true;
			} else {
				connection.commit();
				ps.close();
				return false;
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return false;
		}

	}
	public void updateStatus(String value, String vLicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE FORRENT SET STATUS = ? WHERE VLICENSE = ?");
			ps.setString(1, value);
			ps.setString(2, vLicense);

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

	public String[] getDataFromTable(String tableName, String columns) {
		ArrayList<String> result = new ArrayList<String>();

		try {
			String query = "SELECT ".concat(columns);
			query = query.concat(" FROM " + tableName);
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			String[] listCols = columns.split(",");
			while (rs.next()) {
				for (int i = 1; i <= listCols.length; i++) {
					System.out.println(listCols[i-1] + ": " + rs.getString(i));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return result.toArray(new String[result.size()]);
	}

	public void deleteFromTable(String tableName, String column, String value) {
		try {
			String query = "DELETE FROM " + tableName + " WHERE " + column + " = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, value);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			System.out.println("Successfully deleted");
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertIntoTable(String tableName, String columns, String values) {
		try {
			String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (";
			String[] valuesSplit = values.split(", ");
			for (String value : valuesSplit) {
				query = query.concat("?, ");
			}
			query = query.substring(0, query.length()-2);
			query = query.concat(")");
			PreparedStatement ps = connection.prepareStatement(query);
			for (int i = 1; i <= valuesSplit.length; i++) {
				ps.setString(i, valuesSplit[i-1]);
			}
			ps.executeUpdate();
			connection.commit();
			ps.close();
			System.out.println("Successfully inserted");
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateTable(String tableName, String columns, String values, String whereColumn, String whereValue) {
		try {
			String query = "UPDATE " + tableName + " SET ";
			String[] columnsSplit = columns.split(", ");
			String[] valuesSplit = values.split(", ");
			int numValues = valuesSplit.length;
			for (int i = 0; i < numValues; i++) {
				query = query.concat(columnsSplit[i] + " = ?, ");
			}
			query = query.substring(0, query.length() - 2);
			query = query.concat(" WHERE " + whereColumn + " = ?");
			PreparedStatement ps = connection.prepareStatement(query);
			int i;
			for (i = 1; i <= numValues; i++) {
				ps.setString(i, valuesSplit[i-1]);
			}
			ps.setString(i, whereValue);
			ps.executeUpdate();
			connection.commit();
			ps.close();
			System.out.println("Updated successfully");
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
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

	public int checkVehicleNum(String carType, String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS num FROM ForRent WHERE vtName = ? AND location = ? AND city = ? AND status = 'available'");
			ps.setString(1, carType);
			ps.setString(2, location);
			ps.setString(3, city);
			ResultSet rs = ps.executeQuery();
			connection.commit();
			int vehiclesSatisfyingCriteria = 0;
			if (rs.next()) {
				vehiclesSatisfyingCriteria = rs.getInt(1);
			}
			ps.close();
			return vehiclesSatisfyingCriteria;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return 0;
	}

	public JTable showVehicleDetails(String carType, String location, String city) {
		try {
			String query = "SELECT * FROM ForRent " +
					"WHERE vtName = ? AND location = ? AND city = ? AND status = 'available'";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, carType);
			ps.setString(2, location);
			ps.setString(3, city);

			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			connection.commit();
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return null;
	}

	/**
	 * returns unique used VT for the given arrayList. Use to create column names for daily Reports
	 * @param rbv
	 * @return
	 */
	public ArrayList<String> getvtColumnNames(ArrayList<RentalsByVT> rbv) {
		ArrayList<String> columns = new ArrayList<>();
		for (RentalsByVT elem: rbv) {
			if (!columns.contains(elem.getVtName())) {
				columns.add(elem.getVtName());
			}
		}
		return columns;
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

	// each tuple represent one vtName and the total number of vehicles of that type that are rented on that day
	public JTable rentalsPerBranchVT(String date, String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct ForRent.location, ForRent.city, ForRent.vtname, COUNT(ForRent.vtname) as num\n" +
							"from Rental, ForRent\n" +
							"where Rental.fromDate = ? AND Rental.VLicense = ForRent.vLicense AND ForRent.city = ? AND\n" +
							"\t\t\tForRent.location = ?\n" +
							"group by ForRent.location, ForRent.city, ForRent.vtname");
			ps.setString(1, date);
			ps.setString(2, city);
			ps.setString(3, location);
			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println("No rentals found on queried date");
			return null; // either we can return an empty rs or throw the exception or just return null;
		}
	}
	// should be one tuple that contains the total number of daily rentals at the branch that was specified
	public int totalRentalsOneBranch(String date, String location, String city) {
		int totalRentals = -1;
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct COUNT(Rental.VLicense) as numRentals\n" +
							"from Rental, ForRent\n" +
							"where Rental.fromDate = ? AND Rental.VLicense = ForRent.vLicense AND ForRent.city = ? AND\n" +
							"\t\t\tForRent.location = ?");
			ps.setString(1, date);
			ps.setString(2, city);
			ps.setString(3, location);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				totalRentals = rs.getInt(1);
			}
			return totalRentals;
		} catch (SQLException e) {
			System.out.println("No rentals found on queried date");
			return totalRentals; // either we can return an empty rs or throw the exception or just return null;
		}
	}

	// each tuple is a branch, vtname, number of these type of vehicles that were returns. Each tuple doesn't have to
	// be a unique branch. Each tuple just encodes info about one specific vtName
	public JTable returnForAllBranchesVT(String date) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct ForRent.location, ForRent.city, ForRent.vtname, COUNT(ForRent.vtname) as num, SUM(Return.value) as revenue\n" +
							"from Rental, ForRent, Return\n" +
							"where Rental.rId = Return.rID AND Rental.VLicense = ForRent.vLicense AND Return.returnDate = ?\n" +
							"group by ForRent.location, ForRent.city, ForRent.vtname\t");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println("No return found on queried date");
			return null; // either we can return an empty rs or throw the exception or just return null;
		}
	}

	// each tuple is a branch with the total number of returns for that branch with revenue per branch
	public JTable returnForAllBranches(String date) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct ForRent.location, ForRent.city, COUNT(Rental.VLICENSE) as numReturns, SUM(Return.value) as value\n" +
							"from Rental, ForRent, Return\n" +
							"where Return.returnDate = ? AND Rental.VLicense = ForRent.vLicense AND Return.rID = Rental.rId\n" +
							"group by ForRent.location, ForRent.city");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println("No return found on queried date");
			return null; // either we can return an empty rs or throw the exception or just return null;
		}
	}

	public int grandTotalRevenue(String date) {
		int grandTotal = -1;
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct SUM(Return.value) as totalRevenue\n" +
							"from Return\n" +
							"where Return.returnDate = ?");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				grandTotal = rs.getInt(1);
			}
			ps.close();
			return grandTotal;
		} catch (SQLException e) {
			System.out.println("No return found on queried date");
			return grandTotal;
		}
	}

	// each tuple shows the number of vehicles returned per category, the revenue per category
	public JTable returnForOneBranchVTAndRevenue(String date, String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct ForRent.location, ForRent.city, ForRent.vtname, COUNT(ForRent.vtname) as num, SUM(Return.value) as value\n" +
							"from Rental, ForRent, Return\n" +
							"where Rental.rId = Return.rID AND Rental.VLicense = ForRent.vLicense AND Return.returnDate = ? AND ForRent.city = ? AND ForRent.location = ?\n" +
							"group by ForRent.location, ForRent.city, ForRent.vtname");
			ps.setString(1, date);
			ps.setString(2, city);
			ps.setString(3, location);
			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println("No returns found on queried date");
			return null; // either we can return an empty rs or throw the exception or just return null;
		}
	}

	// revenue and total returned cars for one branch in total for specified day and branch
	public JTable returnOneBranchTotalVtAndRevenue(String date, String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement
					("select distinct SUM(Return.value) as numRevenue, COUNT(Rental.VLicense) as numVehicles\n" +
							"from Rental, ForRent, Return\n" +
							"where Rental.rId = Return.rID AND Rental.VLicense = ForRent.vLicense AND Return.returnDate = ? AND ForRent.city = ? AND ForRent.location = ?\n" +
							"group by ForRent.city, ForRent.location");
			ps.setString(1, date);
			ps.setString(2, city);
			ps.setString(3, location);
			ResultSet rs = ps.executeQuery();
			JTable table = new JTable(buildTableModel(rs));
			ps.close();
			return table;
		} catch (SQLException e) {
			System.out.println("No returns found on queried date");
			return null; // either we can return an empty rs or throw the exception or just return null;
		}
	}

	public ArrayList<String> findAllBranch() {
		ArrayList<String> locations = new ArrayList<String>();
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT location FROM Branch ORDER BY location");
			while (rs.next()) {
				locations.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	public ArrayList<String> findAllCity() {
		ArrayList<String> locations = new ArrayList<String>();
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT city FROM Branch ORDER BY city");
			while (rs.next()) {
				locations.add(rs.getString("city"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	public ArrayList<String> findAllVT() {
		ArrayList<String> locations = new ArrayList<String>();
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT VTName FROM VehicleType");
			while (rs.next()) {
				locations.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

}
