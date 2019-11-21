package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.TimePeriod;
import ca.ubc.cs304.model.*;
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
			Statement stmt = connection.createStatement();
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ForRent WHERE VTNAME = ? AND STATUS = 'available'");
            ps.setString(1, vtName);
            ResultSet vehicle = ps.executeQuery();
           ForRent forRent = createForRentModel(vehicle);
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
            int cardNo = forRent.getOdometer() % 2 + 564979545;
		    // have the reservation made at this confNo. Should be unique because confNo is a primary key
            int rID = confNo / 2;
            Rental rental = new Rental(rID, forRent.getvLicense(), driversLicense, fromDate, fromTime, toDate, toTime,
					forRent.getOdometer(), cardName, cardNo, "02/21", confNo);
            // inserts into database
            insertIntoRental(rental);
            updateStatus( "notavailable", forRent.getvLicense());
            // TODO: display receipt in new UI
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

	public String returnVehicle(TerminalTransactions terminalTransactions) {
	    int rid = -99;
        System.out.println("Enter rental id");
        rid = terminalTransactions.readInteger(false);
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM RENTAL WHERE  RID = ?");
            ps.setInt(1, rid);
            ResultSet rental = ps.executeQuery();
            String vLicense = null;
            Date toDate = null;
            Date fromDate = null;
            String toTime = null;
            while (rental.next()) {
                vLicense = rental.getString(2);
                toDate = rental.getDate(6);
                toTime = rental.getString(7);
                fromDate = rental.getDate(4);

            }
            if (!rentedVehicle(vLicense)) {
                return "Vehicle is not rented";
            }

            PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM FORRENT WHERE  VLICENSE = ?");
            ps1.setString(1, vLicense);
            ResultSet forRent = ps1.executeQuery();
            int odometer = -99;
            String vtName = null;
            while (forRent.next()) {
                odometer = forRent.getInt(7);
                vtName = forRent.getString(9);
            }

            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM VEHICLETYPE WHERE  VTNAME = ?");
            ps2.setString(1, vtName);
            ResultSet vType = ps2.executeQuery();
            VehicleType vehicleType = createVehicleTypeModel(vType);

            // get value
            assert fromDate != null;
            int value = returnValue(vehicleType, fromDate);
            Return ret = new Return(rid, toDate, odometer, 1, value);
            insertIntoReturn(ret);
            //TODO: display receipt
            System.out.println(value);


        } catch (SQLException e) {
            System.out.println("No rental with that id exists");
        }
        return "";

    }

    public int returnValue(VehicleType vehicleType, Date fromDate) {
	    long currTime  = System.currentTimeMillis();
	    long startTime = fromDate.getTime();
        long diff = currTime - startTime;
        long hours = diff * 1000 * 3600;

        long days = Math.floorDiv(hours, 24);

        long weeks = Math.floorDiv(days, 7);
        long daysLeft = (weeks * 7) - days;
        long hoursLeft = hours - (days * 24);

        long weekValue = vehicleType.getWeeklyRate() * weeks;
        long dayValue = vehicleType.getDailyRate() * daysLeft;
        long hourValue = vehicleType.getHourlyRate() * hoursLeft;

        long weekInsValue = vehicleType.getWeeklyInsuranceRate() * weeks;
        long dayInsValue = vehicleType.getDailyInsuranceRate() * daysLeft;
        long hourInsValue = vehicleType.getHourlyInsuranceRate() * hoursLeft;
        return (int) (weekValue + dayValue + hourValue + weekInsValue + dayInsValue + hourInsValue);

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

    public void insertIntoReturn(Return model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RETURN VALUES (?,?,?,?,?)");
            ps.setInt(1, model.getrID());
            ps.setDate(2, model.getReturnDate());
            ps.setInt(3, model.getOdometer());
            ps.setInt(4, model.getFullTank());
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

	public void insertCustomer(String name, String phone, String license, String addr) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)");
			ps.setString(1, phone);
			ps.setString(2, name);
			ps.setString(3, addr);
			ps.setString(4, license);

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}

	public boolean vehicleExist(String vtName, String location, String city) {
		// TODO
		try{
			PreparedStatement ps = connection.prepareStatement("SELECT FROM VEHICLE WHERE vtName = ? AND location = ? AND city = ?");
			ps.setString(1, vtName);
			ps.setString(2, location);
			ps.setString(3, city);
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	public void insertReservation(String license, String location, String city, String vtName, String fromDate, String fromTime, String toDate, String toTime, int reservationNum) {
		try {
			if (vehicleExist(vtName, location, city)) {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?,?,?,?,?,?,?)");
				ps.setInt(1, reservationNum);
				ps.setString(2, vtName);
				ps.setString(3, license);
				ps.setString(4, fromDate);
				ps.setString(5, fromTime);
				ps.setString(6, toDate);
				ps.setString(7, toTime);
				ps.executeQuery();
				connection.commit();
				ps.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
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
	
//    }

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
