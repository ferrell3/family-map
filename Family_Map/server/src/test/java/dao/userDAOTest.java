package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.user;

import static org.junit.Assert.*;


public class userDAOTest {

    private userDAO u;
    private user user1;
    private user user2;

    @Before
    public void setUp() throws Exception {
        //System.out.println("Setting up");
        u = new userDAO();
        user1 = new user("chipsnguac", "password", "chippy@gmail.com", "Chip", "Guacho", "m", "333");
        user2 = new user("mick", "goofyisdumb", "mickmaus@gmail.com", "Mickey", "Mouse", "m", "100");
        u.dropTable();
        u.createTable();
    }

    @After
    public void tearDown() throws Exception {
        u.closeConnection(true);
    }

    @Test
    public void openConnection() throws Exception {
        assertNotNull(u.openConnection());
    }

    @Test
    public void closeConnection() throws Exception {
        u.openConnection();
        assertTrue(u.closeConnection(false));
    }

    @Test
    public void createTable() throws Exception {
        assertTrue(u.createTable());
    }

    @Test
    public void dropTable() throws Exception {
        assertTrue(u.dropTable());
    }

    @Test
    //create valid user
    //repeated user
    public void createUser() throws Exception {
        assertTrue(u.createUser(user1));
        assertFalse(u.createUser(user1)); //fails to add same user twice
    }

    @Test
    public void getUser() throws Exception {
        u.createUser(user2);
        assertTrue(user2.equals(u.getUser(user2.getUserName())));
        assertTrue(u.getUser("notauser").getUserName() == null); //test invalid username
    }

    @Test
    public void deleteUser() throws Exception {
        u.createUser(new user("johnny", "password", "email", "first", "last", "m", "personID"));
        assertTrue(u.deleteUser("johnny"));
    }
}