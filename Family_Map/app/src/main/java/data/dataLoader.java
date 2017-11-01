package data;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.Arrays;
//import java.nio.file.Files;

public class dataLoader {

//    femaleData fD;
    //private String[] fNames;
//    private String[] mNames;
//    private String[] locationData;

    public dataLoader()
    {
        loadData();
    }


    public void loadData() //public void loadData()
    {
        maleData mD = new maleData();
        femaleData fD = new femaleData();
        locationData lD = new locationData();
        surnameData sD = new surnameData();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            JsonReader mdReader = new JsonReader(new FileReader("mnames.json"));
            mD = gson.fromJson(mdReader, maleData.class);

            JsonReader fdReader = new JsonReader(new FileReader("fnames.json"));
            fD = gson.fromJson(fdReader, femaleData.class);

            JsonReader sdReader = new JsonReader(new FileReader("snames.json"));
            sD = gson.fromJson(sdReader, surnameData.class);

            JsonReader ldReader = new JsonReader(new FileReader("locations.json"));
            lD = gson.fromJson(ldReader, locationData.class);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

//        String printFnames = Arrays.toString(fD.data);
//        System.out.println(printFnames);
//
//        String printMnames = Arrays.toString(mD.data);
//        System.out.println(printMnames);
//
//        String printSnames = Arrays.toString(sD.data);
//        System.out.println(printSnames);

//        String printLocs = Arrays.toString(lD.data);
//        System.out.println(printLocs);
        //System.out.println(lD.data.length);
    }



}
