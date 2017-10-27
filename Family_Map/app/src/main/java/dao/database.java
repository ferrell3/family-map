package dao;

import java.sql.*;

public class database {

    //public Connection openConnection()
    // public bool closeConnection()

    private Connection connection = null;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection openConnection() //throws SQLException
    {
        //Connection c = null;
        try{
            final String DBNAME =  "jdbc:sqlite:new.db";
            connection = DriverManager.getConnection(DBNAME); // throws SQLException
            System.out.println("Connection established");
            connection.setAutoCommit(false);
        }catch (SQLException e)
        {
            //throw new DatabaseException("openConnection failed", e);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("openConnection failed");
        }
        return connection;
    }

    public boolean closeConnection(boolean commit)
    {
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
            System.out.println("Connection closed");
            return true;
        }catch (SQLException e)
        {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("closeConnection failed");
            return false;
        }
    }


    public static void main( String args[]) {
        Connection connection = null;
        Statement stmt = null;
        //ResultSet results = null;

        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);

            String dbname = "jdbc:sqlite:test.db";
            connection = DriverManager.getConnection(dbname); // throws SQLException

            stmt = connection.createStatement();

            String createUsers = "CREATE TABLE IF NOT EXISTS users " +
                    "(username TEXT NOT NULL PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "gender TEXT NOT NULL CHECK (gender IN ('f', 'm'))," +
                    "person_id TEXT NOT NULL REFERENCES persons(person_id));";

            String createPersons = "CREATE TABLE IF NOT EXISTS persons " +
                    "(person_id TEXT NOT NULL PRIMARY KEY," +
                    "descendant TEXT NOT NULL REFERENCES users(username)," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "gender TEXT NOT NULL CHECK (gender IN ('f', 'm'))," +
                    "father TEXT REFERENCES persons(person_id)," +
                    "mother TEXT REFERENCES persons(person_id)," +
                    "spouse TEXT REFERENCES persons(person_id));";

            String createEvents = "CREATE TABLE IF NOT EXISTS events " +
                    "(event_id TEXT NOT NULL PRIMARY KEY," +
                    "descendant TEXT NOT NULL REFERENCES users(username)," +
                    "person TEXT NOT NULL REFERENCES persons(person_id)," +
                    "latitude REAL NOT NULL," +
                    "longitude REAL NOT NULL," +
                    "country TEXT NOT NULL," +
                    "city TEXT NOT NULL," +
                    "event_type TEXT NOT NULL," +
                    "year TEXT NOT NULL);";

            String createAuth = "CREATE TABLE IF NOT EXISTS auth " +
                    "(token TEXT NOT NULL PRIMARY KEY," +
                    "user TEXT NOT NULL REFERENCES users(username));";

            stmt.executeUpdate(createUsers);
            stmt.executeUpdate(createPersons);
            stmt.executeUpdate(createEvents);
            stmt.executeUpdate(createAuth);
            stmt.close();

            connection.close();
            connection = null;

        }catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return;
        }


    }

}
