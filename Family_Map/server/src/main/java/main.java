//import dao.*;
//import service.*;
//import dto.*;
//
//
//public class main {
//    public static void main(String args[])
//    {
//        userDAO u = new userDAO();
//        personDAO p = new personDAO();
//        eventDAO e = new eventDAO();
//        authDAO a = new authDAO();
//
//        u.dropTable();
//        p.dropTable();
//        e.dropTable();
//        a.dropTable();
//
//        u.createTable();
//        p.createTable();
//        e.createTable();
//        a.createTable();
//
//
//
//        service s = new service();
//        registerRequest regReq = new registerRequest("chipper", "shinyteeth", "chippy@gmail.com", "Chip", "Skylark", "m");
//        response r = s.register(regReq);
//
//        if(r.getMessage().isEmpty())
//        {
//            System.out.println("Register successful!");
//            System.out.println("Username: " + r.getUserName());
//            System.out.println("AuthToken: " + r.getAuthToken());
//            System.out.println("PersonID: " + r.getPersonId());
//        }
//        else
//        {
//            System.out.println(r.getMessage());
//        }
//
//        //p.deletePerson("chipper");
//        //System.out.println(s.fill("Buffalo Bill"));
//
//
////        loginRequest logReq = new loginRequest("chipper", "shinyteeth");
////
////        response lr = s.login(logReq);
////
////        String chipAuth = lr.getAuthToken();
////
////        if(lr.getMessage().isEmpty())
////        {
////            System.out.println("Login successful!");
////            System.out.println("Username: " + lr.getUserName());
////            System.out.println("AuthToken: " + lr.getAuthToken());
////            System.out.println("PersonID: " + lr.getPersonId());
////        }
////        else
////        {
////            System.out.println(lr.getMessage());
////        }
////
////
////        regReq = new registerRequest("tacojohn", "yoquierotacos", "tacos@tacobell.com", "John", "Tacco", "m");
////        r = s.register(regReq);
////
////        String johnAuth = r.getAuthToken();;
////
////        if(r.getMessage().isEmpty())
////        {
////            System.out.println("Register successful!");
////            System.out.println("Username: " + r.getUserName());
////            System.out.println("AuthToken: " + r.getAuthToken());
////            System.out.println("PersonID: " + r.getPersonId());
////        }
////        else
////        {
////            System.out.println(r.getMessage());
////        }
//
////        System.out.println("One of John's events: ");
////        s.personService()
//
////        System.out.println("John's people: ");
////        System.out.println(s.peopleService(johnAuth).toString());
////
////        System.out.println("Chipper's people's events: ");
////        System.out.println(s.allEventsService(chipAuth).toString());
//
//
//
//        //ACCURACY CHECK
//        //IF DEATH DATES CAN'T BE IN THE FUTURE, CHECK IF THEY'RE PAST 2017, AND IF THEY ARE, SET THEM TO 2017
//
//
//
////        person chip = new person("chipper", "001", "Chip", "Skylark", "m", "002", "003", "004");
////        person pops = new person("chipper", "002", "Papa", "Skylark", "m", "005", "006", "003");
////        person mom = new person("chipper", "003", "Mama", "Skylark", "f", "", "", "002");
////        person wife = new person("chipper", "004", "Wifey", "Skylark", "f", "", "", "001");
////        person gpa = new person("chipper", "005", "Gramps", "Skylark", "m", "", "", "006");
////        person gma = new person("chipper", "006", "Granny", "Skylark", "f", "", "", "005");
////
////        p.createPerson(chip);
////        p.createPerson(pops);
////        p.createPerson(mom);
////        p.createPerson(wife);
////        p.createPerson(gpa);
////        p.createPerson(gma);
////
////        event e1 = new event("chipper", "m01", "001", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
////        event e2 = new event("chipper", "m02", "004", 6.15, 12.3, "USA", "Sacramento", "Marriage", "2013");
////        event e3 = new event("chipper", "m03", "002", 82.69, 76.123, "USA", "New York", "Marriage", "1985");
////
////        e.createEvent(e1);
////        e.createEvent(e2);
////        e.createEvent(e3);
////
////
////        u.getUser("chipper");
////        //p.getPerson("002");
////        p.getPeople("chipper");
////
//////        if(p.getPerson("BonJovi").getPersonId() == null)
//////        {
//////            System.out.println("I was right.");
//////        }
////
////        //e.getEvent("m03");
////        e.getAllEvents("chipper");
//
//    }
//}
