package dto;

/**
 * The personResponse class represents the data transfer object for a person object
 */
public class personResponse {
    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;

    /**
     * User (Username) to which this person belongs
     */
    private String descendant;

    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;

    /**
     *  Person’s last name (non-empty string)
     */
    private String lastName;

    /**
     * : Person’s gender (string: “f” or “m”)
     */
    private String gender;

    /**
     *  ID of person’s father (possibly null)
     */
    private String father;

    /**
     * ID of person’s mother (possibly null)
     */
    private String mother;

    /**
     * ID of person’s spouse (possibly null)
     */
    private String spouse;

    /**
     * String containing a message to describe error
     */
    private String message;

    /**
     * Default empty constructor
     */
    public personResponse() {
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
