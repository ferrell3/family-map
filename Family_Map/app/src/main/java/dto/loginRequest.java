package dto;

/**
 * The loginRequest class represents the body of Login. Passes in the username and password of the user to be logged in
 */
public class loginRequest {
    /**
     * Non-empty string containing the user's unique username
     */
    private String username;

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
        this.username = username;
        this.password = password;
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
}
