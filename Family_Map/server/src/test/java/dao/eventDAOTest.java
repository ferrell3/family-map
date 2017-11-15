package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.event;

import static org.junit.Assert.*;

public class eventDAOTest {

    private eventDAO e;
    private event event1;
    private event event2;

    @Before
    public void setUp() throws Exception {
        e = new eventDAO();
        event1 = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event2 = new event("chipper", "m03", "002", 82.23, 76.56, "USA", "New York", "Marriage", "1985");
        e.dropTable();
        e.createTable();
    }

    @After
    public void tearDown() throws Exception {
        e.closeConnection(true);
    }

    @Test
    public void openConnection() throws Exception {
        assertNotNull(e.openConnection());
    }

    @Test
    public void closeConnection() throws Exception {
        assertTrue(e.closeConnection(false));
    }

    @Test
    public void createEvent() throws Exception {
        assertTrue(e.createEvent(event1));
        assertFalse(e.createEvent(event1)); //test adding the same event twice
    }

    @Test
    public void createTable() throws Exception {
        assertTrue(e.createTable());
    }

    @Test
    public void dropTable() throws Exception {
        assertTrue(e.dropTable());
    }

    @Test
    public void getEvent() throws Exception {
        e.createEvent(event2); //create valid event
        assertTrue(event2.equals(e.getEvent("m03")));
    }

    @Test
    public void getEventInvalid() throws Exception {
        e.createEvent(event2); //create valid event
        assertTrue(e.getEvent("thiseventdoesnotexist").getDescendant() == null);
    }

    @Test
    public void getAllEvents() throws Exception {
        event e1 = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e2 = new event("chipper", "m02", "004", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e3 = new event("chipper", "m03", "002", 82.69, 76.123, "USA", "New York", "Marriage", "1985");

        e.createEvent(e1);
        e.createEvent(e2);
        e.createEvent(e3);

        event[] events = e.getAllEvents("chipper");

        assertTrue(e1.equals(events[0]));
        assertTrue(e2.equals(events[1]));
        assertTrue(e3.equals(events[2]));
    }

    @Test //tests getting events for invalid username
    public void getAllEventsInvalid() throws Exception {
        event e1 = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e2 = new event("chipper", "m02", "004", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e3 = new event("chipper", "m03", "002", 82.69, 76.123, "USA", "New York", "Marriage", "1985");

        e.createEvent(e1);
        e.createEvent(e2);
        e.createEvent(e3);

        //created 3 events for descendant "chipper"
        event[] events = e.getAllEvents("tonythetiger"); // try invalid username

        assertTrue(events.length == 0);
    }

    @Test
    public void deleteEvent() throws Exception {
        e.createEvent(new event("johnny", "eventID", "personID", 12.653, -54.258, "country", "city", "eventType", "year"));
        assertTrue(e.deleteEvent("johnny"));
    }

}