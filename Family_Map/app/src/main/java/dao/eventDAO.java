package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.event;

/**
 * The eventDAO class represents a data access object to access Event data in the database
 */
public class eventDAO {

    private PreparedStatement stmt = null;
    private ResultSet results = null;
    private Connection connection = null;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.print(e.getMessage());
            //e.printStackTrace();
        }
    }

    public Connection openConnection() //throws SQLException
    {
        try{
            final String DBNAME =  "jdbc:sqlite:main.db";
            connection = DriverManager.getConnection(DBNAME); // throws SQLException
            //System.out.println("Connection established");
            connection.setAutoCommit(false);
        }catch (SQLException e)
        {
            System.out.println("openConnection failed");
            System.out.print(e.getMessage());
            //e.printStackTrace();
        }
        return connection;
    }
//
//    public Connection openConnection(String name) //throws SQLException
//    {
//        try{
//            StringBuilder db = new StringBuilder("jdbc:sqlite:");
//            db.append(name);
//            final String DBNAME = db.toString();
//            connection = DriverManager.getConnection(DBNAME); // throws SQLException
//            //System.out.println("Connection established");
//            connection.setAutoCommit(false);
//        }catch (SQLException e)
//        {
//            System.out.println("openConnection failed");
//            //e.printStackTrace();
//        }
//        return connection;
//    }

    public boolean closeConnection(boolean commit)
    {
        if(connection == null)
        {
            return true;
        }
        try {
            if(commit)
            {
                connection.commit();
            }
            else
            {
                connection.rollback();
            }

            connection.close();
            connection = null;
            //System.out.println("Connection closed");
            return true;
        }catch (SQLException e)
        {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("closeConnection failed");
            System.out.print(e.getMessage());
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds an event to the database
     * @param e the event to be inserted
     * @return String message indicating success or failure
     */
    public boolean createEvent(event e)
    {
        boolean status;
        String message;
        try {
            connection = openConnection();

            String createEvent = "INSERT INTO events VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = connection.prepareStatement(createEvent);
            stmt.setString(1, e.getDescendant());
            stmt.setString(2, e.getEventId());
            stmt.setString(3, e.getPersonId());
            stmt.setDouble(4, e.getLatitude());
            stmt.setDouble(5, e.getLongitude());
            stmt.setString(6, e.getCountry());
            stmt.setString(7, e.getCity());
            stmt.setString(8, e.getEventType());
            stmt.setString(9, e.getYear());
            stmt.executeUpdate();
            stmt.close();

            status = true;
            closeConnection(true);
            message = "Event added.";
        }catch (SQLException ex)
        {
            status = false;
            closeConnection(false);
            message = "Create event failed.";
            System.out.print(ex.getMessage());
            //ex.printStackTrace();
        }
        //System.out.println(message);
        return status;
    }


    public boolean createTable()
    {
        boolean status;
        String message;
        try {
            connection = openConnection();
            String createEvents = "CREATE TABLE IF NOT EXISTS events " +
                    "(descendant TEXT NOT NULL REFERENCES users(username)," +
                    "event_id TEXT NOT NULL PRIMARY KEY," +
                    "person TEXT NOT NULL REFERENCES persons(person_id)," +
                    "latitude REAL NOT NULL," +
                    "longitude REAL NOT NULL," +
                    "country TEXT NOT NULL," +
                    "city TEXT NOT NULL," +
                    "event_type TEXT NOT NULL," +
                    "year TEXT NOT NULL);";
            stmt = connection.prepareStatement(createEvents);
            stmt.executeUpdate();
            stmt.close();

            status = true;
            closeConnection(true);
            message = "Events table created.";
        }catch (SQLException e)
        {
            status = false;
            closeConnection(false);
            message = "Create events table failed.";
            System.out.print(e.getMessage());
            //e.printStackTrace();
        }
        //System.out.println(message);
        return status;
    }

    public boolean dropTable()
    {
        boolean status;
        String message;
        try {
            connection = openConnection();

            String dropEvents = "DROP TABLE IF EXISTS events";
            stmt = connection.prepareStatement(dropEvents);
            stmt.executeUpdate();
            stmt.close();

            status = true;
            closeConnection(true);
            message = "Events table dropped.";
        }catch (SQLException e)
        {
            status = false;
            closeConnection(false);
            message = "Drop events table failed.";
            System.out.print(e.getMessage());
            //e.printStackTrace();
        }
        //System.out.println(message);
        return status;
    }


    /**
     * Gets the event object with the specified ID
     * @param eventID the specified ID
     * @return the event with the specified ID
     */
    public event getEvent(String eventID)
    {
        event e = new event();
        try {
            connection = openConnection();
            String query = "SELECT * FROM events WHERE event_id = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, eventID);
            results = stmt.executeQuery();
            while (results.next())
            {
                e.setDescendant(results.getString(1));
                e.setEventId(results.getString(2));
                e.setPersonId(results.getString(3));
                e.setLatitude(results.getDouble(4));
                e.setLongitude(results.getDouble(5));
                e.setCountry(results.getString(6));
                e.setCity(results.getString(7));
                e.setEventType(results.getString(8));
                e.setYear(results.getString(9));

                //print event
                //System.out.printf("descendant: %s  eventID: %s  person: %s  lat: %f  long: %f  country: %s  city: %s  event type: %s  year: %s\n", e.getDescendant(), e.getEventId(), e.getPersonId(), e.getLatitude(), e.getLongitude(), e.getCountry(), e.getCity(), e.getEventType(), e.getYear());
            }

            stmt.close();
            e.setValid(true);
            closeConnection(true);
        }catch (SQLException ex)
        {
            closeConnection(false);
            e.setValid(false);
//            System.out.println("Get event failed.");
//            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }

        return e;
    }

    public event[] getAllEvents(String username)    //ArrayList<event> getAllEvents(String username)
    {
        ArrayList<event> events = new ArrayList<>();
        try {
            connection = openConnection();
            String query = "SELECT * FROM events WHERE descendant = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            while (results.next())
            {
                event e = new event();
                e.setDescendant(results.getString(1));
                e.setEventId(results.getString(2));
                e.setPersonId(results.getString(3));
                e.setLatitude(results.getDouble(4));
                e.setLongitude(results.getDouble(5));
                e.setCountry(results.getString(6));
                e.setCity(results.getString(7));
                e.setEventType(results.getString(8));
                e.setYear(results.getString(9));
                events.add(e);

                //System.out.printf("descendant: %s  eventID: %s  person: %s  lat: %f  long: %f  country: %s  city: %s  event type: %s  year: %s\n", e.getDescendant(), e.getEventId(), e.getPersonId(), e.getLatitude(), e.getLongitude(), e.getCountry(), e.getCity(), e.getEventType(), e.getYear());
            }

            stmt.close();
            closeConnection(true);
        }catch (SQLException ex)
        {
            closeConnection(false);
//            System.out.println("Get events failed.");
//            System.out.print(ex.getMessage());
            //ex.printStackTrace();
        }

        return events.toArray(new event[events.size()]);
        //return events;
    }

    public boolean deleteEvent(String username)
    {
        boolean status;
        String message;
        try {
            connection = openConnection();

            String deleteEvent = "DELETE FROM events WHERE descendant = ?";
            stmt = connection.prepareStatement(deleteEvent);
            stmt.setString(1, username);
            stmt.executeUpdate();
            stmt.close();

            status = true;
            closeConnection(true);
            message = "Event deleted.";
        }catch (SQLException e)
        {
            status = false;
            closeConnection(false);
            message = "Delete event failed.";
            System.out.print(e.getMessage());
            //e.printStackTrace();
        }
        //System.out.println(message);
        return status;
    }
}
