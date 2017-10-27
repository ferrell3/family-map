package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class userTest {
    private user u;
    private user u1;

    @Before
    public void setUp() throws Exception {
        u = new user("chipper", "shinyteeth", "chippy@gmail.com", "Chip", "Skylark", "m", "001");
        u1 = new user();
    }

    @Test
    public void testGetters()
    {
        assertEquals("chipper", u.getUsername());
        assertEquals("shinyteeth", u.getPassword());
        assertEquals("chippy@gmail.com", u.getEmail());
        assertEquals("Chip", u.getFirstName());
        assertEquals("Skylark", u.getLastName());
        assertEquals("m", u.getGender());
        assertEquals("001", u.getPersonId());
    }

    @Test
    public void testSetters()
    {

        u1.setUsername("kipper");
        assertEquals("kipper", u1.getUsername());

        u1.setPassword("shiny");
        assertEquals("shiny", u1.getPassword());

        u1.setEmail("kippy@gmail.com");
        assertEquals("kippy@gmail.com", u1.getEmail());

        u1.setFirstName("Kip");
        assertEquals("Kip", u1.getFirstName());

        u1.setLastName("Klark");
        assertEquals("Klark", u1.getLastName());

        u1.setGender("f");
        assertEquals("f", u1.getGender());

        u1.setPersonId("011");
        assertEquals("011", u1.getPersonId());
    }

}