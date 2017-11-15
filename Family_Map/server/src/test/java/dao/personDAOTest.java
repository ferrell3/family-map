package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.person;

import static org.junit.Assert.*;

public class personDAOTest {

    private personDAO p;
    private person person1;
    private person person2;

    @Before
    public void setUp() throws Exception {
        p = new personDAO();
        person1 = new person("steve-o", "123", "Steve", "Jobs", "m", "456", "789", "741");
        person2 = new person("steve-o", "741", "Nancy", "Jobs", "f", "", "", "123");
        p.dropTable();
        p.createTable();
    }

    @Test
    public void openConnection() throws Exception {
        assertNotNull(p.openConnection());
    }

    @Test
    public void closeConnection() throws Exception {
        assertTrue(p.closeConnection(false));
    }

    @After
    public void tearDown() throws Exception {
        p.closeConnection(true);
    }

    @Test
    public void createPerson() throws Exception {
        assertTrue(p.createPerson(person1));
        assertFalse(p.createPerson(person1)); //test adding same person twice
    }

    @Test
    public void createTable() throws Exception {
        assertTrue(p.createTable());
    }

    @Test
    public void dropTable() throws Exception {
        assertTrue(p.dropTable());
    }

    @Test //tests valid and invalid
    public void getPerson() throws Exception {
        p.createPerson(person2);
        assertTrue(person2.equals(p.getPerson("741")));
        assertTrue(p.getPerson("notaperson").getDescendant() == null);
    }

    @Test //test getting people on valid input and invalid
    public void getPeople() throws Exception {
        person chip = new person("chipper", "001", "Chip", "Skylark", "m", "002", "003", "004");
        person pops = new person("chipper", "002", "Papa", "Skylark", "m", "005", "006", "003");
        person mom = new person("chipper", "003", "Mama", "Skylark", "f", "", "", "002");

        p.createPerson(chip);
        p.createPerson(pops);
        p.createPerson(mom);

        person[] people = p.getPeople("chipper");

        assertTrue(chip.equals(people[0]));
        assertTrue(pops.equals(people[1]));
        assertTrue(mom.equals(people[2]));

        person[] notPeople = p.getPeople("nobody");
        assertTrue(notPeople.length == 0);
    }


    @Test
    public void deletePerson() throws Exception {
        p.createPerson(new person("johnny", "personID", "first", "last", "m", "father", "mother", "spouse"));
        assertTrue(p.deletePerson("johnny"));
    }
}