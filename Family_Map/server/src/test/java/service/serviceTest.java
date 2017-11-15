package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.authDAO;
import dao.eventDAO;
import dao.personDAO;
import dao.userDAO;
import dto.eventResponse;
import dto.loadRequest;
import dto.loginRequest;
import dto.personResponse;
import dto.registerRequest;
import dto.response;
import models.event;
import models.person;
import models.user;

import static org.junit.Assert.*;


public class serviceTest {
    service s;

    userDAO u;
    personDAO p;
    eventDAO e;
    authDAO a;
    response r;

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
        r = s.register(new registerRequest("chipper", "shinyteeth", "chippy@gmail.com", "Chip", "Skylark", "m", "19127"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test //tests valid registration
    public void registerValid() throws Exception {
        registerRequest regReq = new registerRequest("username", "password", "e@mail.com", "First", "Last", "f");
        r = s.register(regReq);
        assertTrue(r.getMessage() == null);
        assertTrue(r.getUserName().equals("username"));
        assertFalse(r.getAuthToken() == null);
        assertFalse(r.getPersonId() == null);
    }

    @Test //tests missing field - username
    public void registerInvalid() throws Exception {
        registerRequest regReq = new registerRequest("", "password", "e@mail.com", "First", "Last", "m");
        response rr = s.register(regReq);
        assertTrue(rr.getMessage().equals("Register failed: Missing input field(s)."));
    }

    @Test //tests valid login
    public void loginValid() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127")));
        response lr = s.login(new loginRequest("tacojohn", "megustatacos"));
        assertTrue(lr.getMessage() == null);
        assertTrue(lr.getUserName().equals("tacojohn"));
        assertFalse(lr.getAuthToken() == null);
        assertFalse(lr.getPersonId() == null);
    }

    @Test //tests incorrect password
    public void loginInvalid() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127")));  //create valid user
        response lr = s.login(new loginRequest("tacojohn", "wrong."));  //test incorrect password
        assertTrue(lr.getMessage().equals("Login failed: Invalid password.")); // message should indicate an error with the password
    }

    @Test //tests valid fill
    public void fillValid() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127"))); //create valid user
        String result = s.fill("tacojohn"); //test default fill
        assertFalse(result.equals("Fill failed: Error adding an event."));
        assertFalse(result.equals("Fill failed: Error adding a person."));
    }

    @Test //tests failure to fill - invalid number of generations
    public void fillInvalid() throws Exception {
        assertTrue(u.createUser(new user("tacojohn", "megustatacos", "tacos@tacobell.com", "John", "Tacco", "m", "19127"))); //create valid user
        String result = s.fill("tacojohn", -2); //fill with invalid generations
        assertTrue(result.equals("Fill failed: Invalid number of generations.")); //message should indicate accurate error
    }

    @Test //valid clear only - the only issues would be internal and would cause this to fail
    public void clear() throws Exception {
        assertEquals("Successfully cleared the database.", s.clear());
    }

    @Test //tests valid load
    public void loadValid() throws Exception {
        user u1 = new user("user1", "the first", "1stU@mail.com", "First", "User", "m", "1");

        user[] uArray = new user[1];
        uArray[0] = u1;

        person p2 = new person("user1", "2", "Second", "Person", "m", "", "", "");
        person p3 = new person("user1", "3", "Third", "Person", "f", "2", "", "");
        person p4 = new person("user1", "4", "Fourth", "Person", "f", "", "3", "");

        person[] pArray = new person[3];
        pArray[0] = p2;
        pArray[1] = p3;
        pArray[2] = p4;

        event e5 = new event("notuser1", "5", "99", 68.284, -50.123, "Country", "city", "type", "1986");
        event e6 = new event("neitheruser", "6", "00", 08.987, 18.456, "country", "city", "type", "1724");

        event[] eArray = new event[2];
        eArray[0] = e5;
        eArray[1] = e6;

        loadRequest lr = new loadRequest();
        lr.setUsers(uArray);
        lr.setPersons(pArray);
        lr.setEvents(eArray);
        assertEquals("Successfully added 1 users, 3 persons, and 2 events to the database.", s.load(lr));
    }

    @Test //tests invalid load, empty arrays
    public void loadInvalid() throws Exception {
        loadRequest lr = new loadRequest();
        assertEquals("Load failed.", s.load(lr));
    }

    @Test
    public void personServiceValid() throws Exception {
        p.createPerson(new person("chipper", "20880", "Nick", "Jones", "m", "", "", ""));
        personResponse pR = s.personService("20880", r.getAuthToken());
        assertTrue(pR.getMessage() == null);
    }

    @Test //tests invalid personID
    public void personServiceInvalid() throws Exception {
        p.createPerson(new person("chipper", "20880", "Nick", "Jones", "m", "", "", ""));
        personResponse pR = s.personService("666", r.getAuthToken()); //invalid personID
        assertTrue(pR.getMessage() != null);
    }

    @Test //tests invalid auth token
    public void peopleServiceInvalid() throws Exception {
        assertFalse(s.peopleService("ajfaiafjeisl").getMessage() == null);
    }

    @Test
    public void peopleServiceValid() throws Exception {
        s.fill("chipper", 3); //fill database with random persons and events for user
        assertTrue(s.peopleService(r.getAuthToken()).getMessage() == null); //pass in the auth token that was generated to get the associated persons
    }

    @Test
    public void eventServiceValid() throws Exception {
        e.createEvent(new event("chipper", "applesauce", "20880", 57.302, 82.402, "country", "city", "type", "2045")); //create valid event
        eventResponse eR = s.eventService("applesauce", r.getAuthToken()); //get response
        assertTrue(eR.getMessage() == null); //null message means it was successful
    }

    @Test //tests invalid auth token
    public void allEventsServiceInvalid() throws Exception {
        assertFalse(s.allEventsService("ajfaiafjeisl").getMessage() == null);
    }

    @Test
    public void allEventsServiceValid() throws Exception {
        s.fill("chipper", 3); //fill database with random persons and events for user
        assertTrue(s.allEventsService(r.getAuthToken()).getMessage() == null); //null message means it was successful
    }
}