package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class eventTest {
    event e;
    event e1;

    @Before
    public void setUp() throws Exception {
        e = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        e1 = new event();
    }

    @Test
    public void testGetters() {
        assertEquals("chipper", e.getDescendant());
        assertEquals("m01", e.getEventId());
        assertEquals("001", e.getPersonId());
        assertTrue(6.15 == e.getLatitude());
        assertTrue(12.3 == e.getLongitude());
        assertEquals("USA", e.getCountry());
        assertEquals("Sacramento", e.getCity());
        assertEquals("Marriage", e.getEventType());
        assertEquals("2013", e.getYear());
    }

    @Test
    public void testSetters()
    {

        e1.setDescendant("kipper");
        assertEquals("kipper", e1.getDescendant());

        e1.setEventId("m10");
        assertEquals("m10", e1.getEventId());

        e1.setPersonId("010");
        assertEquals("010", e1.getPersonId());

        e1.setLatitude(84.93);
        assertTrue(84.93 == e1.getLatitude());

        e1.setLongitude(69.25);
        assertTrue(69.25 == e1.getLongitude());

        e1.setCountry("Canada");
        assertEquals("Canada", e1.getCountry());

        e1.setCity("Alberta");
        assertEquals("Alberta", e1.getCity());

        e1.setEventType("Death");
        assertEquals("Death", e1.getEventType());

        e1.setYear("2120");
        assertEquals("2120", e1.getYear());
    }

}