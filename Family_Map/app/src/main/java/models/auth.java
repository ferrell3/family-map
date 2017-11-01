package models;

/**
 * The auth class represents an auth object in the database
 */
public class auth {

    /**
     * Authorization token
     */
    private String token;

    /**
     * Username of the user to whom the token belongs
     */
    private String user;

    private boolean valid;

    //methods

    /**
     * auth class constructor
     */
    public auth(){}

    public auth(String token, String user)
    {
        this.token = token;
        this.user = user;
    }

    public boolean equals(auth a)
    {
        return (this.token.equals(a.token) && this.user.equals(a.user));
    }

    public boolean isValid() { return valid; }

    public void setValid(boolean bool) { this.valid = bool; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
