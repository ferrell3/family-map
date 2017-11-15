package dto;

/**
 * The eventResponse class represents the data transfer object for an event object
 */
public class eventResponse {
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
    private Double latitude;

    /**
     * Longitude of event’s location
     */
    private Double longitude;

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

    /**
     * String containing a message to describe error
     */
    private String message;

    /**
     * default empty constructor
     */
    public eventResponse() {}

    public eventResponse(String msg)
    {
        this.message = msg;
    }

    public String getEventId() {
        return eventID;
    }

    public void setEventId(String eventID) {
        this.eventID = eventID;
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

    public void setPersonId(String personID) {
        this.personID = personID;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
