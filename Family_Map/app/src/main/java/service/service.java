package service;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import data.dataHandler;
import dto.*;
import dao.*;
import models.auth;
import models.event;
import models.person;
import models.user;

/**
 * The service class contains the methods for each service the server will provide
 */
public class service {

    private userDAO uD;
    private personDAO pD;
    private eventDAO eD;
    private authDAO aD;
    private dataHandler dH;


    /**
     * Default constructor
     */
    public service(){
        uD = new userDAO();
        pD = new personDAO();
        eD = new eventDAO();
        aD = new authDAO();
        dH = new dataHandler();

        uD.createTable();
        pD.createTable();
        eD.createTable();
        aD.createTable();
    }


    public String randomString()
    {
        return UUID.randomUUID().toString();
    }

    public int randomNumber(int x)
    {
        Random random = new Random();
        int num = random.nextInt(2 * x) - x;
        return num;
    }

    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns the user's auth token.
     *
     * @param r The registerRequest object containing the data to be transferred
     * @return response object containing the username, authorization token and assigned person ID or a message describing an error
     */
    public response register(registerRequest r)
    {
        response rsp = new response();
        //CHECK FOR ERRORS WITH USER (already existing username, etc.)
        if(r.getUsername().isEmpty() || r.getPassword().isEmpty() || r.getEmail().isEmpty() ||
                r.getFirstName().isEmpty() || r.getLastName().isEmpty() || r.getGender().isEmpty())
        {
            rsp.setMessage("Register failed: Missing input field(s).");
            return rsp;
        }
        if(uD.getUser(r.getUsername()).isValid())
        {
            //username is taken
            rsp.setMessage("Register failed: That username is already taken.");
            return rsp;
        }
        //create new user account
        String personID = randomString();
        user u = new user(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
        if(!uD.createUser(u))
        {
            //error creating user
            rsp.setMessage("Register failed: Error creating user.");
            return rsp;
        }

        //generate ancestor data
        fill(u.getUserName());

        //log the user in
        loginRequest lR = new loginRequest(u.getUserName(), u.getPassword());

        return login(lR);
    }


    /**
     * Logs in the user and returns the user's auth token.
     *
     * @param r The loginRequest object containing the data to be transferred
     * @return response object containing the username, authorization token and assigned person ID or a message describing an error
     */
    public response login(loginRequest r)
    {
        response logRsp = new response();
        String username = r.getUsername();
        String password = r.getPassword();

        //check for missing properties
        if(r.getUsername().isEmpty() || r.getPassword().isEmpty())
        {
            logRsp.setMessage("Login failed: Missing input field(s).");
            return logRsp;
        }

        String token = randomString();
        auth a = new auth(token, username);

        user u = uD.getUser(username);

        //check for valid username
        if(u.getUserName() == null)
        {
            logRsp.setMessage("Login failed: Invalid username.");
        }
        //check password
        else if(!password.equals(u.getPassword()))
        {
            logRsp.setMessage("Login failed: Invalid password.");
        }
        //check auth token creation? What could cause an error here?
        else if(!aD.createAuth(a))
        {
            logRsp.setMessage("Login failed: Error creating authorization token.");
        }
        else
        {
            logRsp.setUsername(username);
            logRsp.setAuthToken(token);
            logRsp.setPersonId(u.getPersonId());
            logRsp.setMessage("");
        }
        return logRsp;
    }


    /**
     *  Deletes ALL data from the database, including user accounts, auth tokens, and
     *  generated person and event data
     * @return String message indicating success or error
     */
    public String clear(){
        String msg;
        boolean dropped = uD.dropTable() &&
                pD.dropTable() &&
                eD.dropTable() &&
                aD.dropTable();

        boolean created = uD.createTable() &&
                pD.createTable() &&
                eD.createTable() &&
                aD.createTable();

        if(!dropped)
        {
            msg = "Error erasing the database.";
        }
        else if(!created)
        {
            msg = "Error recreating the database.";
        }
        else
        {
            msg = "Successfully cleared the database.";
        }
        return msg;
    }


    /**
     * Calls fill function and passes in default number of generations if none is given
     * @param username The specified user name
     * @return String message indicating success or error
     */
    public String fill(String username){
        return fill(username, 4);
    }

    /**
     * Populates the server's database with generated data for the specified user name.
     * @param username The specified user name
     * @param generations The specified number of generations
     * @return String message indicating success or error
     */
    public String fill(String username, int generations)
    {
//        System.out.println(pD.deletePerson(username));
//        System.out.println(eD.deleteEvent(username));

        if(!uD.getUser(username).isValid())
        {
            return "Fill failed: Invalid username.";
        }
        else if(generations < 0)
        {
            return "Fill failed: Invalid number of generations.";
        }

        pD.deletePerson(username);
        eD.deleteEvent(username);

        TreeMap<Integer, ArrayList<person>> genMap = new TreeMap<>();
        person userP = new person();
        String status;
        boolean check;  // error check
        int countPpl = 0;
        int countEvents = 0;
        for(int i = generations; i > 0; i--)
        {
            ArrayList<person> currGen = new ArrayList<>();
            double range = (Math.pow(2, i))/2;
            //System.out.println("range: " + range);
            for(int j = 0; j < range; j++)
            {
                person p1 = dH.newMale(username);
                person p2 = dH.newFemale(username);

                p1.setSpouse(p2.getPersonId());
                p2.setSpouse(p1.getPersonId());

                //add couple to map
                currGen.add(p1);
                currGen.add(p2);

                //create events
                int year = (2000 + randomNumber(3)) - ((i * 25) + randomNumber(3));
                int year2;

                //create birth
                event birth1 = dH.newEvent("Birth", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(3);
                event birth2 = dH.newEvent("Birth", p2.getPersonId(), username, Integer.toString(year2));

                //create baptism
                year += (11 + randomNumber(3));
                event baptism1 = dH.newEvent("Baptism", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(2);
                event baptism2 = dH.newEvent("Baptism", p2.getPersonId(), username, Integer.toString(year2));

                //create Marriage
                year += (14 + randomNumber(5));
                event mar1 = dH.newEvent("Marriage", p1.getPersonId(), username, Integer.toString(year));
                //copy Marriage event to female
                event mar2 = new event();
                mar2.setDescendant(mar1.getDescendant());
                mar2.setEventId(randomString());
                mar2.setPersonId(p2.getPersonId());
                mar2.setLatitude(mar1.getLatitude());
                mar2.setLongitude(mar1.getLongitude());
                mar2.setCountry(mar1.getCountry());
                mar2.setCity(mar1.getCity());
                mar2.setEventType("Marriage");
                mar2.setYear(Integer.toString(year));

                //create death
                year += (45 + randomNumber(11));
                while (year > 2017) { year = 2017 - randomNumber(3); }
                event death1 = dH.newEvent("Death", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(4);
                while (year2 > 2017) { year2 = 2017 - randomNumber(3); }
                event death2 = dH.newEvent("Death", p2.getPersonId(), username, Integer.toString(year2));

                check = eD.createEvent(birth1) &&
                eD.createEvent(birth2) &&
                eD.createEvent(baptism1) &&
                eD.createEvent(baptism2) &&
                eD.createEvent(mar1) &&
                eD.createEvent(mar2) &&
                eD.createEvent(death1) &&
                eD.createEvent(death2);

                if(!check) //check for errors from adding events
                {
                    status = "Fill failed: Error adding an event.";
                    return status;
                }
                else
                {
                    countEvents += 8;
                }

//                if (!eD.createEvent(birth1)) { System.out.println("Error creating birth1"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(birth2)){ System.out.println("Error creating birth2"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(baptism1)) { System.out.println("Error creating bap1"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(baptism2)) { System.out.println("Error creating bap2"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(mar1)) { System.out.println("Error creating mar1"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(mar2)) { System.out.println("Error creating mar2"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(death1)) { System.out.println("Error creating death1"); }
//                else { countEvents++; }
//
//                if(!eD.createEvent(death2)) { System.out.println("Error creating death2"); }
//                else { countEvents++; }
            }
            //add gen to map
            genMap.put(i, currGen);
        }

        //get the user's father and set father's last name to the user's last name
        genMap.get(1).get(0).setLastName(uD.getUser(username).getLastName());

        //attach children to parents
        for(int k = 1; k < generations; k++) //start with the first generation
        {
            ArrayList<person> parents = genMap.get(k);
            ArrayList<person> grandparents = genMap.get(k+1);
            int gpIndex = 0;
            for (person p : parents)
            {
                //This will set the last names to be the same for each family, but maintain the female's maiden name
                grandparents.get(gpIndex).setLastName(p.getLastName());

                p.setFather(grandparents.get(gpIndex).getPersonId());
                gpIndex++;
                p.setMother(grandparents.get(gpIndex).getPersonId());
                gpIndex++;
                check = pD.createPerson(p); //add each person as they get parents added
                if(!check) //check for errors from previous adding events
                {
                    status = "Fill failed: Error adding a person.";
                    return status;
                }
                countPpl++;
            }
        }

        //add the furthest generation to the database
        for(person p : genMap.get(generations))
        {
            check = pD.createPerson(p);
            if(!check) //check for errors from previous adding events
            {
                status = "Fill failed: Error adding a person.";
                return status;
            }
            countPpl++;
        }

        //create person for user in DB
        user u = uD.getUser(username);
        userP.setPersonId(u.getPersonId());
        userP.setDescendant(username);
        userP.setFirstName(u.getFirstName());
        userP.setLastName(u.getLastName());
        userP.setGender(u.getGender());
        userP.setFather(genMap.get(1).get(0).getPersonId());
        userP.setMother(genMap.get(1).get(1).getPersonId());
        check = pD.createPerson(userP);
        if(!check) //check for errors from previous adding events
        {
            status = "Fill failed: Error adding a person.";
            return status;
        }
        countPpl++;

        StringBuilder out = new StringBuilder();
        out.append("Successfully added " + countPpl + " people and " + countEvents + " events to the database.");
        //System.out.println(out);
        return out.toString();
    }


    /**
     * Clears all data from the database (just like the /clear API), and then
     * loads the posted user, person, and event data into the database.
     * @param r The loadRequest object containing the data to be transferred
     * @return String message indicating success or error
     */
    public String load(loadRequest r){
        boolean fail = false;
        clear();

        for(user u : r.getUsers())
        {
            if(!uD.createUser(u))
            {
                fail = true;
            }
        }

        for(person p : r.getPersons())
        {
            if(!pD.createPerson(p))
            {
                fail = true;
            }
        }

        for(event e : r.getEvents())
        {
            if(eD.createEvent(e))
            {
                fail = true;
            }
        }

        if(fail)
        {
            return "Load failed.";
        }

        StringBuilder result = new StringBuilder();
        result.append("Successfully added " + r.getUsers().length + " users, " + r.getPersons().length + " persons, and " + r.getEvents().length + " events to the database.");

        return result.toString();
    }


    /**
     * Finds the single Person object with the specified ID.
     * @param personID The specified Person ID
     * @param authToken The associated authorization token
     * @return The personResponse object containing the person information associated with the specified ID
     */
    public personResponse personService(String personID, String authToken)
    {
        personResponse pRsp = new personResponse();

        //invalid auth token
        auth a = aD.getAuth(authToken);
        if(!a.isValid())
        {
            pRsp.setMessage("Person request failed: Invalid authorization token.");
            return pRsp;
        }

        person p = pD.getPerson(personID);
        //invalid personID parameter
        if(!p.isValid())
        {
            pRsp.setMessage("Person request failed: Invalid personID.");
            return pRsp;
        }

        //person does not belong to this user
        if(!p.getDescendant().equals(a.getUser()))
        {
            pRsp.setMessage("Person request failed: The requested person does not belong to this user.");
            return pRsp;
        }

        pRsp.setDescendant(p.getDescendant());
        pRsp.setPersonId(p.getPersonId());
        pRsp.setFirstName(p.getFirstName());
        pRsp.setLastName(p.getLastName());
        pRsp.setGender(p.getGender());
        pRsp.setFather(p.getFather());
        pRsp.setMother(p.getMother());
        pRsp.setSpouse(p.getSpouse());

        return pRsp;
    }


    /**
     * Returns ALL family members of the current user. The
     * current user is determined from the provided auth token.
     * @param authToken The current user's authorization token
     * @return The peopleResponse object containing the person information for all family members of the current user
     */
    public peopleResponse peopleService(String authToken){
        peopleResponse pplRsp = new peopleResponse();

        //invalid auth token
        auth a = aD.getAuth(authToken);
        if(!a.isValid())
        {
            pplRsp.setMessage("Persons request failed: Invalid authorization token.");
            return pplRsp;
        }

        pplRsp.setData(pD.getPeople(a.getUser()));

        return pplRsp;
    }


    /**
     * Finds the single Event object with the specified ID
     * @param eventID The event ID to be found
     * @param authToken The current user's authorization token
     * @return The eventResponse object containing the event information associated with the specified ID
     */
    public eventResponse eventService(String eventID, String authToken)
    {
        eventResponse eRsp = new eventResponse();

        //invalid auth token
        auth a = aD.getAuth(authToken);
        if(!a.isValid())
        {
            eRsp.setMessage("Event request failed: Invalid authorization token.");
            return eRsp;
        }

        event e = eD.getEvent(eventID);
        //invalid eventID parameter
        if(!e.isValid())
        {
            eRsp.setMessage("Event request failed: Invalid eventID.");
            return eRsp;
        }

        //person does not belong to this user
        if(!e.getDescendant().equals(a.getUser()))
        {
            eRsp.setMessage("Event request failed: The requested event does not belong to this user.");
            return eRsp;
        }

        eRsp.setDescendant(e.getDescendant());
        eRsp.setPersonId(e.getPersonId());
        eRsp.setEventId(e.getEventId());
        eRsp.setLatitude(e.getLatitude());
        eRsp.setLongitude(e.getLongitude());
        eRsp.setCountry(e.getCountry());
        eRsp.setCity(e.getCity());
        eRsp.setEventType(e.getEventType());
        eRsp.setYear(e.getYear());

        return eRsp;
    }

    /**
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param authToken The current user's authorization token
     * @return The allEventsResponse object containing the event information for all events of all family members of the current user
     */
    public allEventsResponse allEventsService(String authToken){
        allEventsResponse evsRsp = new allEventsResponse();

        //invalid auth token
        auth a = aD.getAuth(authToken);
        if(!a.isValid())
        {
            evsRsp.setMessage("Events request failed: Invalid authorization token.");
            return evsRsp;
        }

        evsRsp.setData(eD.getAllEvents(a.getUser()));

        return evsRsp;
    }
}
