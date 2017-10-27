package dto;

/**
 * The response class represents a data transfer object for a user object
 */
public class response {
    //FIELDS

    /**
     * Non-empty string containing the user's unique username
     */
    private String username;


    /**
     * Non-empty string containing the authorization token
     */
    private String authToken;


    /**
     *  Non-empty string containing the Person ID of the user’s generated Person object
     */
    private String personId;


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
     * @param user username
     * @param token authorization token
     * @param pid person ID
     */
    //* @param msg message String describing error or null if successful
    public response(String user, String token, String pid){
        this.username = user;
        this.authToken = token;
        this.personId = pid;
    }

    /**
     * Class constructor that initializes message field with the given parameter
     * @param msg message String describing error
     */
    public response(String msg){
        this.message = msg;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
