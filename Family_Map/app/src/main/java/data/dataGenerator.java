package data;


import java.util.UUID;
import dao.*;
import models.event;
import models.person;

public class dataGenerator {

    //private userDAO uD;
    private personDAO pD;
    private eventDAO eD;
    private authDAO aD;



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

    //maleData
    public person newMale()
    {

        return null;
    }

    public person newFemale()
    {

        return null;
    }

    public event newEvent(String eventType, String personID, String descendant, String year)
    {
        event e = new event();

        //generate random stuff here
        //random eventID
        e.setEventId(randomString());

        //random latitude
        //random longitude
        //random country
        //random city

        e.setYear(year);

        return e;
    }
}
