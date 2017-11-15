package dto;

/**
 * The loginRequest class represents the body of Login. Passes in the username and password of the user to be logged in
 */
public class loginRequest {
    /**
     * Non-empty string containing the user's unique username
     */
    private String userName;

    /**
     * Non-empty string containing the user's password
     */
    private String password;

    /**
     * Default empty constructor
     */
    public loginRequest() {
    }

    /**
     * Class constructor that initializes fields with parameters
     * @param username User's username
     * @param password User's password
     */
    public loginRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
