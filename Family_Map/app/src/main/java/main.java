import dao.*;
import service.*;
import models.*;
import dto.*;

public class main {
    public static void main(String args[])
    {
        userDAO u = new userDAO();
        personDAO p = new personDAO();
        eventDAO e = new eventDAO();
        authDAO a = new authDAO();

        u.dropTable();
        p.dropTable();
        e.dropTable();
        a.dropTable();

        u.createTable();
        p.createTable();
        e.createTable();
        a.createTable();

        user uObj = new user("chipper", "shinyteeth", "chippy@gmail.com", "Chip", "Skylark", "m", "001");
        u.createUser(uObj);

        person chip = new person("chipper", "001", "Chip", "Skylark", "m", "002", "003", "004");
        person pops = new person("chipper", "002", "Papa", "Skylark", "m", "005", "006", "003");
        person mom = new person("chipper", "003", "Mama", "Skylark", "f", "", "", "002");
        person wife = new person("chipper", "004", "Wifey", "Skylark", "f", "", "", "001");
        person gpa = new person("chipper", "005", "Gramps", "Skylark", "m", "", "", "006");
        person gma = new person("chipper", "006", "Granny", "Skylark", "f", "", "", "005");

        p.createPerson(chip);
        p.createPerson(pops);
        p.createPerson(mom);
        p.createPerson(wife);
        p.createPerson(gpa);
        p.createPerson(gma);

        event e1 = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e2 = new event("chipper", "m02", "004", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
        event e3 = new event("chipper", "m03", "002", 82.69, 76.123, "USA", "New York", "Marriage", "1985");

        e.createEvent(e1);
        e.createEvent(e2);
        e.createEvent(e3);


        u.getUser("chipper");
        //p.getPerson("002");
        p.getPeople("chipper");

//        if(p.getPerson("BonJovi").getPersonId() == null)
//        {
//            System.out.println("I was right.");
//        }

        //e.getEvent("m03");
        e.getAllEvents("chipper");

    }
}
