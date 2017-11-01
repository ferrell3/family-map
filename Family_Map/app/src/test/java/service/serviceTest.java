package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.authDAO;
import dao.eventDAO;
import dao.personDAO;
import dao.userDAO;
import dto.loginRequest;
import dto.registerRequest;
import dto.response;
import models.user;

import static org.junit.Assert.*;


public class serviceTest {
    @Test
    public void clear() throws Exception {
        assertEquals("Successfully cleared the database.", s.clear());
    }

    @Test
    public void load() throws Exception {

    }

    @Test
    public void personService() throws Exception {

    }

    @Test
    public void peopleService() throws Exception {

    }

    @Test
    public void eventService() throws Exception {

    }

    @Test
    public void allEventsService() throws Exception {

    }

    service s;
    userDAO u;
    personDAO p;
    eventDAO e;
    authDAO a;

    @Before
    public void setUp() throws Exception {
        u = new userDAO();
        p = new personDAO();
        e = new eventDAO();
        a = new authDAO();

        u.dropTable();
        p.dropTable();
        e.dropTable();
        a.dropTable();

        u.createTable();
        p.createTable();
        e.createTable();
        a.createTable();

        s = new service();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void register() throws Exception {
        registerRequest regReq = new registerRequest("chipper", "shinyteeth", "chippy@gmail.com", "Chip", "Skylark", "m");
        response r = s.register(regReq);
        assertTrue(r.getMessage().isEmpty());
        assertTrue(r.getUsername().equals("chipper"));
        assertFalse(r.getAuthToken().isEmpty());
        assertFalse(r.getPersonId().isEmpty());
    }

    @Test
    public void login() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127")));
        response lr = s.login(new loginRequest("tacojohn", "megustatacos"));
        assertTrue(lr.getMessage().isEmpty());
        assertTrue(lr.getUsername().equals("tacojohn"));
        assertFalse(lr.getAuthToken().isEmpty());
        assertFalse(lr.getPersonId().isEmpty());
    }

    @Test
    public void fill() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127")));
        String result = s.fill("tacojohn");
        assertFalse(result.equals("Fill failed: Error adding an event."));
        assertFalse(result.equals("Fill failed: Error adding a person."));
    }
}