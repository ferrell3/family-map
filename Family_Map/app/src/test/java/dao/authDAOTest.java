package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.auth;

import static org.junit.Assert.*;


public class authDAOTest {
    private authDAO a;
    private auth auth1;
    private auth auth2;

    @Before
    public void setUp() throws Exception {
        a = new authDAO();
        a.dropTable();
        a.createTable();
        auth1 = new auth("jdf23", "chipper");
        auth2 = new auth("mjf75", "mike");
    }

    @After
    public void tearDown() throws Exception {
        a.closeConnection(true);
    }

    @Test
    public void openConnection() throws Exception {
        assertNotNull(a.openConnection());
    }

    @Test
    public void closeConnection() throws Exception {
        assertTrue(a.closeConnection(false));
    }

    @Test
    public void createTable() throws Exception {
        assertEquals("Auth table created.", a.createTable());
    }

    @Test
    public void dropTable() throws Exception {
        assertEquals("Auth table dropped.", a.dropTable());
    }

    @Test
    public void createAuth() throws Exception {
        assertEquals("Auth added.", a.createAuth(auth1));
        assertNotEquals("Auth added.", a.createAuth(auth1)); //repeated token
    }

    @Test
    public void getAuth() throws Exception {
        a.createAuth(auth2);       //assertEquals("Auth added.", a.createAuth(auth2));
        assertTrue(auth2.equals(a.getAuth(auth2.getToken())));
    }

}