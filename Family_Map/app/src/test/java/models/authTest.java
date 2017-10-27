package models;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;


public class authTest {
    auth a;
    auth a1;

    @Before
    public void setUp() throws Exception {
        a = new auth("jdf23", "chipper");
        a1 = new auth();
    }

    @Test
    public void testGetters() {
        assertEquals("jdf23", a.getToken());
        assertEquals("chipper", a.getUser());
    }

    @Test
    public void testSetters() {
        a1.setToken("mjf75");
        assertEquals("mjf75", a1.getToken());

        a1.setUser("mike");
        assertEquals("mike", a1.getUser());
    }
}