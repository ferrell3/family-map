package models;


/**
 * The event class represents an event object in the database
 */
public class event {

    //FIELDS-----------------

    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;

    /**
     * User (Username) to which this person belongs
     */
    private String descendant;

    /**
     * ID of person to which this event belongs
     */
    private String personID;

    /**
     * Latitude of event’s location
     */
    private double latitude;

    /**
     * Longitude of event’s location
     */
    private double longitude;

    /**
     * Country in which event occurred
     */
    private String country;

    /**
     * City in which event occurred
     */
    private String city;

    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;

    /**
     * Year in which event occurred
     */
    private String year;

    private boolean valid;

    //METHODS--------------

    /**
     * Default empty constructor
     */
    public event(){}

    public event(String des, String eID, String pID, double lat, double longit, String country, String city, String type, String year)
    {
        this.descendant = des;
        this.eventID = eID;
        this.personID = pID;
        this.latitude = lat;
        this.longitude = longit;
        this.country = country;
        this.city = city;
        this.eventType = type;
        this.year = year;
    }

    public boolean equals(event e)
    {
        return this.descendant.equals(e.descendant) &&
        this.eventID.equals(e.eventID) &&
        this.personID.equals(e.personID) &&
        this.latitude == e.latitude &&
        this.longitude == e.longitude &&
        this.country.equals(e.country) &&
        this.city.equals(e.city) &&
        this.eventType.equals(e.eventType) &&
        this.year.equals(e.year);
    }

    public boolean isValid() { return valid; }

    public void setValid(boolean bool) { this.valid = bool; }

    public String getEventId() {
        return eventID;
    }

    public void setEventId(String eventId) {
        this.eventID = eventId;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String person) {
        this.personID = person;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String  getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String toString()
    {
        StringBuilder out = new StringBuilder();
        out.append("descendant: " + descendant + "\neventID: " + eventID + "\npersonID: " + personID + "\nlatitude: " + latitude
        + "\nlongitude: " + longitude + "\ncountry: " + country + "\ncity: " + city + "\neventType: " + eventType + "\nyear: " + year);
        return out.toString();
    }
}
