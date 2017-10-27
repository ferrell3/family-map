package service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import data.dataGenerator;
import dto.*;
import dao.*;
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
    private dataGenerator dG;


    /**
     * Default constructor
     */
    public service(){
        uD = new userDAO();
        pD = new personDAO();
        eD = new eventDAO();
        aD = new authDAO();
    }


    public String randomString()
    {
        String str = UUID.randomUUID().toString();
        //check tables
        while(pD.getPerson(str).getDescendant() != null || eD.getEvent(str).getDescendant() != null || aD.getAuth(str).getUser() != null)
        {
            str = UUID.randomUUID().toString();
        }
        return str;
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
        response regRsp = new response();

        //CHECK FOR ERRORS WITH USER (already existing username, etc.)

        //create new user account
        String personID = randomString();
        user u = new user(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
        uD.createUser(u);

        //generate ancestor data
        fill(u.getUsername());

        //log the user in
        loginRequest lR = new loginRequest(u.getUsername(), u.getPassword());
        response logRsp = login(lR);

        //create response
        regRsp.setUsername(logRsp.getUsername());
        regRsp.setPersonId(logRsp.getPersonId());
        regRsp.setAuthToken(logRsp.getAuthToken());

        return regRsp;
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

        //

        return logRsp;
    }


    /**
     *  Deletes ALL data from the database, including user accounts, auth tokens, and
     *  generated person and event data
     * @return String message indicating success or error
     */
    public String clear(){
        //if any return false, return an error message...?
        uD.dropTable();
        pD.dropTable();
        eD.dropTable();
        aD.dropTable();

        ////////finish this
        return null;
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
        TreeMap<Integer, ArrayList<person>> genMap = new TreeMap<>();
        person userP = new person();

        for(int i = generations; i > 0; i--)
        {
            ArrayList<person> currGen = new ArrayList<>();
            for(int j = 0; j < ((2^i)/2); j++)
            {
                person p1 = dG.newMale();
                person p2 = dG.newFemale();

                p1.setDescendant(username);
                p2.setDescendant(username);

                p1.setSpouse(p2.getPersonId());
                p2.setSpouse(p1.getPersonId());

                //add couple to map
                currGen.add(p1);
                currGen.add(p2);

                //create events
                int year = (2015 + randomNumber(3)) - ((generations * 25) + randomNumber(3));
                int year2;

                //create birth
                event birth1 = dG.newEvent("birth", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(3);
                event birth2 = dG.newEvent("birth", p2.getPersonId(), username, Integer.toString(year2));

                eD.createEvent(birth1);
                eD.createEvent(birth2);

                //create baptism
                year += (11 + randomNumber(3));
                event baptism1 = dG.newEvent("baptism", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(2);
                event baptism2 = dG.newEvent("baptism", p2.getPersonId(), username, Integer.toString(year2));

                eD.createEvent(baptism1);
                eD.createEvent(baptism2);

                //create marriage
                year += (17 + randomNumber(5));
                event mar1 = dG.newEvent("marriage", p1.getPersonId(), username, Integer.toString(year));
                event mar2 = mar1;
                mar2.setEventId(randomString());
                mar2.setPersonId(p2.getPersonId());

                eD.createEvent(mar1);
                eD.createEvent(mar2);

                //create death
                year += (60 + randomNumber(11));
                event death1 = dG.newEvent("birth", p1.getPersonId(), username, Integer.toString(year));
                year2 = year + randomNumber(3);
                event death2 = dG.newEvent("birth", p2.getPersonId(), username, Integer.toString(year2));

                eD.createEvent(death1);
                eD.createEvent(death2);
            }
            //add gen to map
            genMap.put(i, currGen);
        }

        //attach children to parents
        for(int k = 1; k < generations; k++)
        {
            ArrayList<person> parents = genMap.get(k);
            ArrayList<person> grandparents = genMap.get(k+1);
            int gpIndex = 0;
            for (person p : parents)
            {
                p.setFather(grandparents.get(gpIndex).getPersonId());
                gpIndex++;
                p.setMother(grandparents.get(gpIndex).getPersonId());
                gpIndex++;
                pD.createPerson(p); //add each person as they get parents added
            }
        }

        //add the furthest generation to the database
        for(person p : genMap.get(generations))
        {
            pD.createPerson(p);
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
        pD.createPerson(userP);


        /////////////////////////////////////////////////////////finish return
        return null;
    }


    /**
     * Clears all data from the database (just like the /clear API), and then
     * loads the posted user, person, and event data into the database.
     * @param r The loadRequest object containing the data to be transferred
     * @return String message indicating success or error
     */
    public String load(loadRequest r){
        return null;
    }


    /**
     * Finds the single Person object with the specified ID.
     * @param personId The specified Person ID
     * @param authToken The associated authorization token
     * @return The personResponse object containing the person information associated with the specified ID
     */
    public personResponse personService(String personId, String authToken){
        return null;
    }


    /**
     * Returns ALL family members of the current user. The
     * current user is determined from the provided auth token.
     * @param authToken The current user's authorization token
     * @return The peopleResponse object containing the person information for all family members of the current user
     */
    public peopleResponse peopleService(String authToken){
        return null;
    }


    /**
     * Finds the single Event object with the specified ID
     * @param eventId The event ID to be found
     * @param authToken The current user's authorization token
     * @return The eventResponse object containing the event information associated with the specified ID
     */
    public eventResponse eventService(String eventId, String authToken){
        return null;
    }

    /**
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param authToken The current user's authorization token
     * @return The allEventsResponse object containing the event information for all events of all family members of the current user
     */
    public allEventsResponse allEventsService(String authToken){
        return null;
    }
}
