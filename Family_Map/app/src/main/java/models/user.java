package models;

/**
 * The user class represents a user object in the database
 */
public class user {

    /**
     * Unique user name (non-empty string)
     */
    private String username;

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
    private String personId;

    /**
     * Default empty constructor
     */
    public user(){
    }

    public user(String username, String pass, String email, String first, String last, String gender, String pid)
    {
        this.username = username;
        this.password = pass;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.gender = gender;
        this.personId = pid;
    }

    public boolean equals(user u)
    {
        if( this.username.equals(u.username) &&
                this.password.equals(u.password) &&
                this.email.equals(u.email) &&
                this.firstName.equals(u.firstName) &&
                this.lastName.equals(u.lastName) &&
                this.gender.equals(u.gender) &&
                this.personId.equals(u.personId)) {
            return true;
        }
        else return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
