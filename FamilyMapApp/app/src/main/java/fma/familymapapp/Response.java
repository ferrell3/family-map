package fma.familymapapp;


public class Response {
    private String userName;
    private String authToken;
    private String personID;
    private String message;


    public Response(){
    }


    public Response(String user, String token, String pid){
        this.userName = user;
        this.authToken = token;
        this.personID= pid;
        this.message = null;
    }

    public Response(String msg){
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
