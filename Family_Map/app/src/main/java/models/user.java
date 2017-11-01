package models;

/**
 * The user class represents a user object in the database
 */
public class user {

    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    /**
     * User's password
     */
    private String password;

    /**
     * User's email address
     */
    private String email;

    /**
     * User's first name
     */
    private String firstName;

    /**
     * User's last name
     */
    private String lastName;

    /**
     * User's gender
     */
    private String gender;

    /**
     * Unique Person ID assigned to this user
     */
    private String personID;

    private boolean valid;

    /**
     * Default empty constructor
     */
    public user(){
    }

    public user(String userName, String pass, String email, String first, String last, String gender, String pid)
    {
        this.userName = userName;
        this.password = pass;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.gender = gender;
        this.personID = pid;
    }

    public boolean equals(user u)
    {
        if( this.userName.equals(u.userName) &&
                this.password.equals(u.password) &&
                this.email.equals(u.email) &&
                this.firstName.equals(u.firstName) &&
                this.lastName.equals(u.lastName) &&
                this.gender.equals(u.gender) &&
                this.personID.equals(u.personID)) {
            return true;
        }
        else return false;
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean bool)
    {
        valid = bool;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }
}
