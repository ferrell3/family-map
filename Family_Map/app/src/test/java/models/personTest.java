package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class personTest {

    person p;
    person p1;

    @Before
    public void setUp() throws Exception {
        p = new person("chipper", "001", "Chip", "Skylark", "m", "002", "", "004");
        p1 = new person();
    }

    @Test
    public void testGetters()
    {
        assertEquals("chipper", p.getDescendant());
        assertEquals("001", p.getPersonId());
        assertEquals("Chip", p.getFirstName());
        assertEquals("Skylark", p.getLastName());
        assertEquals("m", p.getGender());
        assertEquals("002", p.getFather());
        assertEquals("", p.getMother());
        assertEquals("004", p.getSpouse());
    }

    @Test
    public void testSetters()
    {

        p1.setDescendant("kipper");
        assertEquals("kipper", p1.getDescendant());

        p1.setFirstName("Kip");
        assertEquals("Kip", p1.getFirstName());

        p1.setLastName("Klark");
        assertEquals("Klark", p1.getLastName());

        p1.setGender("f");
        assertEquals("f", p1.getGender());

        p1.setPersonId("010");
        assertEquals("010", p1.getPersonId());

        p1.setFather("020");
        assertEquals("020", p1.getFather());

        p1.setMother("030");
        assertEquals("030", p1.getMother());

        p1.setSpouse("");
        assertEquals("", p1.getSpouse());
    }

}