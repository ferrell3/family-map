package data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.Random;
import java.util.UUID;
import dao.*;
import models.event;
import models.person;

public class dataHandler {

    private personDAO pD;
    private eventDAO eD;
    private authDAO aD;
    private locationData lData;
    private maleData mData;
    private femaleData fData;
    private surnameData sData;

    public dataHandler()
    {
        loadData();
    }


    public void loadData()
    {
        mData = new maleData();
        fData = new femaleData();
        lData = new locationData();
        sData = new surnameData();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            JsonReader mdReader = new JsonReader(new FileReader("mnames.json"));
            mData = gson.fromJson(mdReader, maleData.class);

            JsonReader fdReader = new JsonReader(new FileReader("fnames.json"));
            fData = gson.fromJson(fdReader, femaleData.class);

            JsonReader sdReader = new JsonReader(new FileReader("snames.json"));
            sData = gson.fromJson(sdReader, surnameData.class);

            JsonReader ldReader = new JsonReader(new FileReader("locations.json"));
            lData = gson.fromJson(ldReader, locationData.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public String randomString()
    {
        return UUID.randomUUID().toString();
    }

    //maleData
    public person newMale(String descendant)
    {
        person male = new person();
        male.setPersonId(randomString());
        male.setDescendant(descendant);

        Random random = new Random();
        int randIndex = random.nextInt(mData.data.length);

        male.setFirstName(mData.data[randIndex]);

        randIndex = random.nextInt(sData.data.length);

        male.setLastName(sData.data[randIndex]);
        male.setGender("m");

        return male;
    }

    public person newFemale(String descendant)
    {
        person female = new person();
        female.setPersonId(randomString());
        female.setDescendant(descendant);

        Random random = new Random();
        int randIndex = random.nextInt(fData.data.length);

        female.setFirstName(fData.data[randIndex]);

        randIndex = random.nextInt(sData.data.length);

        female.setLastName(sData.data[randIndex]);
        female.setGender("f");

        return female;
    }

    public event newEvent(String eventType, String personID, String descendant, String year)
    {
        event e = new event();
        e.setDescendant(descendant);
        e.setPersonId(personID);
        e.setEventType(eventType);
        e.setYear(year);

        //generate random data
        String id = randomString();
        e.setEventId(id);


        //random location
        Random random = new Random();
        int randIndex = random.nextInt(lData.data.length);

        e.setLatitude(lData.data[randIndex].latitude);
        e.setLongitude(lData.data[randIndex].longitude);
        e.setCountry(lData.data[randIndex].country);
        e.setCity(lData.data[randIndex].city);

        return e;
    }
}
