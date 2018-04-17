package data;

import model.HODRank;
import model.Rank;
import model.Engine;
import model.Ship;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.DeflectionHeader;
import model.DeflectionRecord;
import net.proteanit.sql.DbUtils;

/**
 * This class is responsible for handling all CRUD operations with the database.
 * Most of the returned types are primitive values.
 *
 * @author Saminda Permauna
 */
public class DbConnect {

    private static final String DB_HOST = "jdbc:h2:file:./webdeflectiontest";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "PrIyA@198#";

    // SQL query strings
    private static final String SHIP_SELECT = "SELECT * FROM WEBDEFLECTION.SHIP";
    private static final String ENGINE_SELECT = "SELECT * FROM WEBDEFLECTION.ENGINE";
    private static final String ENGINE_SELECT_FOR_MODEL = "SELECT SHIPNAME, ENGINENAME, ENGINETYPE, NOOFCYLINDERS, NOOFBEARINGS, FIRINGORDER, INCHARGENAME, INCHARGERANK, WORKERNAME, WORKERRANK FROM WEBDEFLECTION.ENGINE INNER JOIN WEBDEFLECTION.SHIP ON (ENGINE.SHIPID = SHIP.SHIPID)";
    private static final String DEFLECTION_HEADER_SELECT = "SELECT * FROM WEBDEFLECTION.DEFLECTION_HEADER WHERE (ENGINEID = ?)";
    private static final String DEFLECTION_HEADER_SELECT_FROM_ID = "SELECT * FROM WEBDEFLECTION.DEFLECTION_HEADER WHERE (HEADERID = ?)";
    private static final String DEFLECTION_HEADER_SELECT_FOR_MODEL = "SELECT HEADERID, DATE, TIME, CRANKCASETEMP, BOTTOMERROR, CHECKERROR FROM WEBDEFLECTION.DEFLECTION_HEADER WHERE (ENGINEID = ?)";
    private static final String DEFLECTION_SELECT_FROM_ID = "SELECT * FROM WEBDEFLECTION.DEFLECTION WHERE (HEADERID = ?)";

    private static final String SHIP_INSERT = "INSERT INTO WEBDEFLECTION.SHIP (SHIPNAME) VALUES (?);";
    private static final String ENGINE_INSERT = "INSERT INTO WEBDEFLECTION.ENGINE(SHIPID, ENGINENAME, ENGINETYPE, NOOFCYLINDERS, NOOFBEARINGS, FIRINGORDER, INCHARGENAME, INCHARGERANK, WORKERNAME, WORKERRANK) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String DEFLECTION_HEADER_INSERT = "INSERT INTO WEBDEFLECTION.DEFLECTION_HEADER (ENGINEID, DATE, TIME, CRANKCASETEMP, BOTTOMERROR, CHECKERROR) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DEFLECTION_INSERT = "INSERT INTO WEBDEFLECTION.DEFLECTION VALUES (?, ? , ?, ?, ?, ? , ?);";

    private static final String SHIP_DELETE = "DELETE FROM WEBDEFLECTION.SHIP WHERE (SHIPID = ?)";
    private static final String ENGINE_DELETE = "DELETE FROM WEBDEFLECTION.ENGINE WHERE (SHIPID = ?)";

    /**
     * Get a connection to the database.
     *
     * @return java.sql.Connection
     * @throws java.lang.Exception
     */
    public static Connection getDbConnection() throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
        return conn;
    }

    /**
     * Get the ship ID from the ship name.
     *
     * @param shipName
     * @return int
     * @throws Exception
     */
    public static int getShipId(String shipName) throws Exception {
        int shipId = 0;
        String sql = SHIP_SELECT + " WHERE (SHIPNAME = ?)";

        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, shipName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                shipId = rs.getInt("SHIPID");
            }

            return shipId;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get a single ship from the database.
     *
     * @param shipId
     * @return Ship
     * @throws Exception
     */
    public static Ship getShip(int shipId) throws Exception {
        Ship ship = new Ship();
        String sql = SHIP_SELECT + " WHERE (SHIPID = ?)";

        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, shipId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ship.setShipId(rs.getInt("SHIPID"));
                ship.setShipName(rs.getString("SHIPNAME"));
            }

            return ship;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get a list of ship names.
     *
     * @return List of Ship
     * @throws java.lang.Exception
     */
    public static List<Ship> getShipList() throws Exception {
        List<Ship> shipList = new ArrayList<>();

        try (Connection conn = DbConnect.getDbConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(SHIP_SELECT);

            while (rs.next()) {
                Ship ship = new Ship();
                ship.setShipId(rs.getInt("SHIPID"));
                ship.setShipName(rs.getString("SHIPNAME"));
                shipList.add(ship);
            }

            return shipList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get a collection of engines.
     *
     * @return List of Engine
     * @throws Exception
     */
    public static List<Engine> getEngineList() throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(ENGINE_SELECT);

            List<Engine> engineList = mapEngines(rs);

            return engineList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get a list of engines for a specific ship.
     *
     * @param shipId
     * @return List of Engine
     * @throws Exception
     */
    public static List<Engine> getEngineList(int shipId) throws Exception {
        String sql = ENGINE_SELECT + " WHERE (SHIPID = ?)";

        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, shipId);
            ResultSet rs = pst.executeQuery();
            List<Engine> engineList = mapEngines(rs);

            return engineList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Map record set to engine objects.
     *
     * @param rs
     * @return List of Engine
     * @throws Exception
     */
    private static List<Engine> mapEngines(ResultSet rs) throws Exception {
        List<Engine> engineList = new ArrayList<>();

        while (rs.next()) {
            Engine engine = new Engine();
            engine.setEngineId(rs.getInt("ENGINEID"));
            engine.setShipId(rs.getInt("SHIPID"));
            engine.setEngineName(rs.getString("ENGINENAME"));
            engine.setEngineType(rs.getString("ENGINETYPE"));
            engine.setNoOfCylinders(rs.getInt("NOOFCYLINDERS"));
            engine.setNoOfBearings(rs.getInt("NOOFBEARINGS"));
            engine.setFiringOrder(rs.getInt("FIRINGORDER"));
            engine.setInchargeName(rs.getString("INCHARGENAME"));
            engine.setInchargeRank(HODRank.valueOf(rs.getString("INCHARGERANK")));
            engine.setWorkerName(rs.getString("WORKERNAME"));
            engine.setWorkerRank(Rank.valueOf(rs.getString("INCHARGERANK")));

            engineList.add(engine);
        }

        return engineList;
    }

    /**
     * Insert a ship record into the database.
     *
     * @param ship
     * @return Boolean
     * @throws Exception
     */
    public static boolean insertShip(Ship ship) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(SHIP_INSERT);
            pst.setString(1, ship.getShipName());
            pst.execute();
        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    /**
     * Insert a engine record into the database.
     *
     * @param engine
     * @return Boolean
     * @throws Exception
     */
    public static boolean insertEngine(Engine engine) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(ENGINE_INSERT);
            pst.setInt(1, engine.getShipId());
            pst.setString(2, engine.getEngineName());
            pst.setString(3, engine.getEngineType());
            pst.setInt(4, engine.getNoOfCylinders());
            pst.setInt(5, engine.getNoOfBearings());
            pst.setInt(6, engine.getFiringOrder());
            pst.setString(7, engine.getInchargeName());
            pst.setString(8, engine.getInchargeRank().toString());
            pst.setString(9, engine.getWorkerName());
            pst.setString(10, engine.getWorkerRank().toString());

            pst.execute();
        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    /**
     * Get a swing table model object to be displayed on the setup page.
     *
     * @return TableModel
     * @throws Exception
     */
    public static TableModel getSetupTabelModel() throws Exception {
        TableModel tableModel = new DefaultTableModel();

        try (Connection conn = DbConnect.getDbConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(ENGINE_SELECT_FOR_MODEL);

            tableModel = DbUtils.resultSetToTableModel(rs);
        } catch (Exception ex) {
            throw ex;
        }

        return tableModel;
    }

    /**
     * Delete all engine records for a specific ship from the database.
     *
     * @param ShipId
     * @return Boolean
     * @throws Exception
     */
    public static boolean deleteEngines(int ShipId) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(ENGINE_DELETE);
            pst.setInt(1, ShipId);

            pst.execute();
        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    /**
     * Delete a specific ship record from the database.
     *
     * @param ShipId
     * @return Boolean
     * @throws Exception
     */
    public static boolean deleteShip(int ShipId) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(SHIP_DELETE);
            pst.setInt(1, ShipId);

            pst.execute();
        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    /**
     * Insert deflection header info into the DB.
     *
     * @param header
     * @return
     * @throws Exception
     */
    private static long insertDeflectionHeader(DeflectionHeader header) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_HEADER_INSERT, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, header.getEngineId());
            pst.setDate(2, header.getDate());
            pst.setTime(3, header.getTime());
            pst.setDouble(4, header.getCracnkCaseTemp());
            pst.setDouble(5, header.getBottomError());
            pst.setDouble(6, header.getCheckError());

            pst.executeUpdate();
            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }
        } catch (Exception ex) {
            throw ex;
        }

        return 0;
    }

    /**
     * Insert a single deflection record into the database.
     *
     * @param record
     * @return
     * @throws Exception
     */
    private static boolean insertDeflectionRecord(DeflectionRecord record) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_INSERT);
            pst.setLong(1, record.getHeaderId());
            pst.setInt(2, record.getCylinderId());
            pst.setInt(3, record.getNearBdcStbd());
            pst.setInt(4, record.getStarboard());
            pst.setInt(5, record.getTop());
            pst.setInt(6, record.getPort());
            pst.setInt(7, record.getNearBdcPort());

            pst.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    /**
     * Insert a set of deflection records into the DB with the header data.
     *
     * @param records
     * @param header
     * @return
     * @throws Exception
     */
    public static boolean insertDeflectionRecordsWithHeader(Map<Integer, DeflectionRecord> records, DeflectionHeader header) throws Exception {
        long headerId = insertDeflectionHeader(header);

        if (headerId != 0) {
            for (DeflectionRecord record : records.values()) {
                record.setHeaderId(headerId);
                insertDeflectionRecord(record);
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a specific header from the header Id.
     *
     * @param headerId
     * @return
     * @throws Exception
     */
    public static DeflectionHeader getDeflectionHeader(long headerId) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_HEADER_SELECT_FROM_ID);
            pst.setLong(1, headerId);
            ResultSet rs = pst.executeQuery();

            List<DeflectionHeader> headerList = mapHeaders(rs);
            return headerList.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get a list of deflection record headers for the given engine.
     *
     * @param engine
     * @return
     * @throws Exception
     */
    public static List<DeflectionHeader> getDeflectionRecordHeaders(Engine engine) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_HEADER_SELECT);
            pst.setInt(1, engine.getEngineId());
            ResultSet rs = pst.executeQuery();

            return mapHeaders(rs);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Map the result set to a list of headers.
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private static List<DeflectionHeader> mapHeaders(ResultSet rs) throws Exception {
        List<DeflectionHeader> headerList = new ArrayList<>();

        while (rs.next()) {
            DeflectionHeader header = new DeflectionHeader();
            header.setHeaderId(rs.getInt("HEADERID"));
            header.setEngineId(rs.getInt("ENGINEID"));
            header.setDate(rs.getDate("DATE"));
            header.setTime(rs.getTime("TIME"));
            header.setCracnkCaseTemp(rs.getDouble("CRANKCASETEMP"));
            header.setBottomError(rs.getDouble("BOTTOMERROR"));
            header.setCheckError(rs.getDouble("CHECKERROR"));
            headerList.add(header);
        }

        return headerList;
    }

    /**
     * Get the list of deflection records according to header Id.
     *
     * @param headerId
     * @return
     * @throws Exception
     */
    public static Map<Integer, DeflectionRecord> getRecords(long headerId) throws Exception {
        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_SELECT_FROM_ID);
            pst.setLong(1, headerId);
            ResultSet rs = pst.executeQuery();

            return mapRecords(rs);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Map the list to DefelctionRecord objects.
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private static Map<Integer, DeflectionRecord> mapRecords(ResultSet rs) throws Exception {
        SortedMap<Integer, DeflectionRecord> records = new TreeMap<>();

        while (rs.next()) {
            DeflectionRecord record = new DeflectionRecord();
            int cylinderId = rs.getInt("CYLINDERID");
            record.setCylinderId(cylinderId);
            record.setNearBdcStbd(rs.getInt("NEARBDCSTBD"));
            record.setStarboard(rs.getInt("STARBOARD"));
            record.setTop(rs.getInt("TOP"));
            record.setPort(rs.getInt("PORT"));
            record.setNearBdcPort(rs.getInt("NEARBDCPORT"));

            records.put(cylinderId, record);
        }

        return records;
    }

    /**
     * Get the data model for open records dialog.
     *
     * @param engine
     * @return
     * @throws Exception
     */
    public static TableModel getRecordsTabelModel(Engine engine) throws Exception {
        TableModel tableModel = new DefaultTableModel();

        try (Connection conn = DbConnect.getDbConnection()) {
            PreparedStatement pst = conn.prepareStatement(DEFLECTION_HEADER_SELECT_FOR_MODEL);
            pst.setInt(1, engine.getEngineId());
            ResultSet rs = pst.executeQuery();

            tableModel = DbUtils.resultSetToTableModel(rs);
        } catch (Exception ex) {
            throw ex;
        }

        return tableModel;
    }
}
