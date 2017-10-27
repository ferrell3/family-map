package dto;

import models.person;

/**
 * The peopleResponse class represents the response body of the Person request and returns ALL family members of the current user
 */
public class peopleResponse {

    /**
     * Array of person objects
     */
    private person[] data;
    //private personResponse[] data;

    /**
     * String containing a message to describe error
     */
    private String message;

    //json object?

    /**
     * Default empty constructor
     */
    public peopleResponse(){}

    public person[] getData() {
        return data;
    }

    public void setData(person[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
