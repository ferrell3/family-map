package data;


public class locationData {
    location[] data;

    public location[] getData(){
        return data;
    }

    class location {
        String country;
        String city;
        double latitude;
        double longitude;

        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("country: " + country);
            //sb.append(country)
            sb.append("\ncity: " + city);
            sb.append("\nlatitude: " + latitude);
            sb.append("\nlongitude: " + longitude + "\n");
            return sb.toString();
        }
    }
}
