package dao;

import java.sql.*;
import models.user;

/**
 * The userDAO class represents a data access object to access User data in the database
 */
public class userDAO {

    private PreparedStatement stmt = null;
    private ResultSet results = null;
    private Connection connection = null;

    //Used for testing purposes
//    public Connection getConnection()
//    {
//        return connection;
//    }

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
        try{
            final String DBNAME =  "jdbc:sqlite:main.db";
            connection = DriverManager.getConnection(DBNAME); // throws SQLException
            //System.out.println("Connection established");
            connection.setAutoCommit(false);
        }catch (SQLException e)
        {
            System.out.println("openConnection failed");
            //e.printStackTrace();
        }
        return connection;
    }

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
            System.out.println("closeConnection failed");
            //e.printStackTrace();
            return false;
        }
    }

    public String createTable() // return Success or Error String? Or message saying already exists?
    {
        //boolean result = false;
        String message;
        try {
            connection = openConnection();

            String createUsers = "CREATE TABLE IF NOT EXISTS users " +
                    "(username TEXT NOT NULL PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "gender TEXT NOT NULL CHECK (gender IN ('f', 'm'))," +
                    "person_id TEXT NOT NULL REFERENCES persons(person_id));";
            stmt = connection.prepareStatement(createUsers);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Users table created.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create users table failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
        //return result;
    }

    public String dropTable()
    {
        String message;
        try {
            connection = openConnection();

            String dropUsers = "DROP TABLE IF EXISTS users";
            stmt = connection.prepareStatement(dropUsers);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Users table dropped.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Drop users table failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }


    /**
     * Adds a user object to the database
     * @param u The user to be added to the database
     * @return String message indicating success or failure
     */
    public String createUser(user u)
    {
        String message;
        try {
            connection = openConnection();

            String createUser = "INSERT INTO users VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = connection.prepareStatement(createUser);
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonId());
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "User added.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create user failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }


    /**
     * Gets the user object specified by the username and approved with the user's password
     * @param username The specified user
     * @return The specified user
     */
    public user getUser(String username) //get a SINGLE user
    {
        user u = new user();
        try {
            connection = openConnection();
            String query = "SELECT * FROM users WHERE username = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            u.setUsername(results.getString(1));
            u.setPassword(results.getString(2));
            u.setEmail(results.getString(3));
            u.setFirstName(results.getString(4));
            u.setLastName(results.getString(5));
            u.setGender(results.getString(6));
            u.setPersonId(results.getString(7));

            System.out.printf("username: %s  password: %s  email: %s  first name: %s  last name: %s  gender: %s  personID: %s\n", u.getUsername(), u.getPassword(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getGender(), u.getPersonId());

            stmt.close();
            closeConnection(true);
        }catch (SQLException e)
        {
            closeConnection(false);
            System.out.println("Get user failed.");
            //e.printStackTrace();
        }
        return u;
    }
}
