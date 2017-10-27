package dao;

import models.auth;
import java.sql.*;

/**
 * The authDAO class represents a data access object to access Authorization data in the database
 */
public class authDAO {

    private PreparedStatement stmt = null;
    private ResultSet results = null;
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

    public String createTable()
    {
        String message;
        try {
            connection = openConnection();

            String createAuth = "CREATE TABLE IF NOT EXISTS auth " +
                    "(token TEXT NOT NULL PRIMARY KEY," +
                    "user TEXT NOT NULL REFERENCES users(username));";
            stmt = connection.prepareStatement(createAuth);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Auth table created.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create auth table failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }

    public String dropTable()
    {
        String message;
        try {
            connection = openConnection();

            String dropAuth = "DROP TABLE IF EXISTS auth";
            stmt = connection.prepareStatement(dropAuth);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Auth table dropped.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Drop auth table failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }


    /**
     * Adds an auth object to the database
     * @param a The auth object to be added
     * @return String message indicating success or failure
     */
    public String createAuth(auth a)
    {
        String message;
        try {
            connection = openConnection();
            String create = "INSERT INTO auth VALUES ( ?, ? ) ";
            stmt = connection.prepareStatement(create);
            stmt.setString(1, a.getToken());
            stmt.setString(2, a.getUser());
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Auth added.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create auth failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }


    public auth getAuth(String token)
    {
        auth a = new auth();
        try {
            connection = openConnection();
            String query = "SELECT * FROM auth WHERE token = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, token);
            results = stmt.executeQuery();
            a.setToken(results.getString(1));
            a.setUser(results.getString(2));

            System.out.printf("username: %s  token: %s \n", a.getUser(), a.getToken());

            stmt.close();
            closeConnection(true);
        }catch (SQLException e)
        {
            closeConnection(false);
            System.out.println("Get auth failed.");
            //e.printStackTrace();
        }
        return a;
    }
}