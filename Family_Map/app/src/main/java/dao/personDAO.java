package dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.person;

/**
 * The personDAO class represents a data access object to access Person data in the database
 */
public class personDAO {

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

    /**
     * Inserts a person into the database
     * @param p The person object to be added
     * @return String message indicating success or failure
     */
    public String createPerson(person p) //, String username)
    {
        String message;
        try {
            connection = openConnection();

            String createPerson = "INSERT INTO persons VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = connection.prepareStatement(createPerson);
            stmt.setString(1, p.getDescendant());
            stmt.setString(2, p.getPersonId());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFather());
            stmt.setString(7, p.getMother());
            stmt.setString(8, p.getSpouse());
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Person added.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create person failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }

    public String createTable()
    {
        String message;
        try {
            connection = openConnection();

            String createPersons = "CREATE TABLE IF NOT EXISTS persons " +
                    "(descendant TEXT NOT NULL REFERENCES users(username)," +
                    "person_id TEXT NOT NULL PRIMARY KEY," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "gender TEXT NOT NULL CHECK (gender IN ('f', 'm'))," +
                    "father TEXT REFERENCES persons(person_id)," +
                    "mother TEXT REFERENCES persons(person_id)," +
                    "spouse TEXT REFERENCES persons(person_id));";
            stmt = connection.prepareStatement(createPersons);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Persons table created.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Create persons table failed.";
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

            String dropPersons = "DROP TABLE IF EXISTS persons";
            stmt = connection.prepareStatement(dropPersons);
            stmt.executeUpdate();
            stmt.close();

            closeConnection(true);
            message = "Persons table dropped.";
        }catch (SQLException e)
        {
            closeConnection(false);
            message = "Drop persons table failed.";
            //e.printStackTrace();
        }
        //System.out.println(message);
        return message;
    }


    /**
     * Gets the person object with the specified ID
     * @param personId The ID of the person to get
     * @return The person object with the specified ID
     */
    public person getPerson(String personId) //SINGLE person
    {
        person p = new person();
        try {
            connection = openConnection();
            String query = "SELECT * FROM persons WHERE person_id = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1,personId);
            results = stmt.executeQuery();
            while (results.next())
            {
                p.setDescendant(results.getString(1));
                p.setPersonId(results.getString(2));
                p.setFirstName(results.getString(3));
                p.setLastName(results.getString(4));
                p.setGender(results.getString(5));
                p.setFather(results.getString(6));
                p.setMother(results.getString(7));
                p.setSpouse(results.getString(8));

                //print person
                System.out.printf("descendant: %s  personID: %s  first name: %s  last name: %s  gender: %s  father: %s  mother: %s  spouse: %s\n", p.getDescendant(), p.getPersonId(), p.getFirstName(), p.getLastName(), p.getGender(), p.getFather(), p.getMother(), p.getSpouse());
            }

            stmt.close();
            closeConnection(true);
        }catch (SQLException e)
        {
            closeConnection(false);
            System.out.println("Get person failed.");
            //e.printStackTrace();
        }

        return p;
    }

    //getPeople
    public person[] getPeople(String username) {   //ArrayList<person> getPeople(String username) {
        ArrayList<person> people = new ArrayList<>();
        try {
            connection = openConnection();
            String query = "SELECT * FROM persons WHERE descendant = ? ";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            while (results.next()) {
                person p = new person();
                p.setDescendant(results.getString(1));
                p.setPersonId(results.getString(2));
                p.setFirstName(results.getString(3));
                p.setLastName(results.getString(4));
                p.setGender(results.getString(5));
                p.setFather(results.getString(6));
                p.setMother(results.getString(7));
                p.setSpouse(results.getString(8));
                people.add(p);

                //print person
                System.out.printf("descendant: %s  personID: %s  first name: %s  last name: %s  gender: %s  father: %s  mother: %s  spouse: %s\n", p.getDescendant(), p.getPersonId(), p.getFirstName(), p.getLastName(), p.getGender(), p.getFather(), p.getMother(), p.getSpouse());
            }

            stmt.close();
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            System.out.println("Get people failed.");
            //e.printStackTrace();
        }

        //person[] peeps = people.toArray(new person[people.size()]);
        return people.toArray(new person[people.size()]);
        //return people;
    }
}