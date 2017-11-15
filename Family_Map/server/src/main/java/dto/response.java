package dto;

/**
 * The response class represents a data transfer object for a user object
 */
public class response {
    //FIELDS

    /**
     * Non-empty string containing the user's unique userName
     */
    private String userName;


    /**
     * Non-empty string containing the authorization token
     */
    private String authToken;


    /**
     *  Non-empty string containing the Person ID of the user’s generated Person object
     */
    private String personID;


    /**
     * String containing a message to describe error
     */
    private String message;


    //METHODS

    /**
     * Default empty constructor
     */
    public response(){
    }

    /**
     * Class constructor that initializes fields with the given parameters
     * @param user userName
     * @param token authorization token
     * @param pid person ID
     */
    public response(String user, String token, String pid){
        this.userName = user;
        this.authToken = token;
        this.personID= pid;
        this.message = null;
    }

    /**
     * Class constructor that initializes message field with the given parameter
     * @param msg message String describing error
     */
    public response(String msg){
        this.message = msg;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personID) {
        this.personID= personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
