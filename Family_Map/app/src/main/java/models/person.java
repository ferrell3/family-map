package models;

/**
 * The person class represents a person object in the database
 */
public class person {

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
    private String father; // = "";

    /**
     * ID of person’s mother (possibly null)
     */
    private String mother; // = "";

    /**
     * ID of person’s spouse (possibly null)
     */
    private String spouse; // = "";

    private boolean valid;

    /**
    * Default empty constructor
    */
    public person() {
    }

    public person(String descendant, String personId, String first, String last, String gen, String pa, String ma, String spouse)
    {
        this.descendant = descendant;
        this.personID = personId;
        this.firstName = first;
        this.lastName = last;
        this.gender = gen;
        this.father = pa;
        this.mother = ma;
        this.spouse = spouse;
    }

    public boolean equals(person p)
    {
        return this.descendant.equals(p.descendant) &&
        this.personID.equals(p.personID) &&
        this.firstName.equals(p.firstName) &&
        this.lastName.equals(p.lastName) &&
        this.gender.equals(p.gender) &&
        this.father.equals(p.father) &&
        this.mother.equals(p.mother) &&
        this.spouse.equals(p.spouse);
    }

    public boolean isValid() { return valid; }

    public void setValid(boolean bool) { this.valid = bool; }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
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

    public String toString()
    {
        StringBuilder out = new StringBuilder();
        out.append("descendant: " + descendant + "\npersonID: " + personID + "\nfirstName: "
                + firstName + "\nlastName: " + lastName + "\ngender: " + gender + "\nfather: "
                + father + "\nmother: " + mother + "\nspouse: " + spouse);
        return out.toString();
    }
}
