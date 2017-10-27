package dto;

/**
 * The registerRequest class represents the body of Register. Passes in the information for the registering user
 */
public class registerRequest {

    /**
     * Non-empty string containing the user's unique username
     */
    private String username;

    /**
     * Non-empty string containing the user's password
     */
    private String password;

    /**
     * Non-empty string containing the user's email address
     */
    private String email;

    /**
     * Non-empty string containing the user's first name
     */
    private String firstName;

    /**
     * Non-empty string containing the user's last name
     */
    private String lastName;

    /**
     * Non-empty string containing the user's gender
     */
    private String gender;

    //vvvvvvvvvvv FOR LOADREQUEST vvvvvvvvvvvvv
    /**
     * String containing the user's assigned personId
     * (For use with load request)
     */
    private String personId;


    /**
     * Default empty constructor
     */
    public registerRequest() {
    }

    /**
     * Class constructor that initializes member fields with parameters
     * @param user User's unique username
     * @param pass User's password
     * @param email User's email address
     * @param first User's first name
     * @param last User's last name
     * @param gender User's gender
     */
    public registerRequest(String user, String pass, String email,
                           String first, String last, String gender) {
        this.username = user;
        this.password = pass;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.gender = gender;
    }

    //FOR LOADREQUEST
    /**
     * Class constructor that initializes member fields with parameters and includes Person ID
     * @param user User's unique username
     * @param pass User's password
     * @param email User's email address
     * @param first User's first name
     * @param last User's last name
     * @param gender User's gender
     * @param pid personId User's associated Person ID
     */
    public registerRequest(String user, String pass, String email,
                           String first, String last, String gender, String pid) {
        this.username = user;
        this.password = pass;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.gender = gender;
        this.personId = pid;
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
