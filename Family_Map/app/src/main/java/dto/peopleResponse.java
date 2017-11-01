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

    public void setData(person[] ppl)
    {
        this.data = ppl.clone();
    }

    public void setData1(person[] ppl) {
        for(int i = 0; i < ppl.length; i++)
        {
            data[i] = ppl[i];
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString()
    {
        StringBuilder out = new StringBuilder();
        if(data.length != 0)
        {
            for(person p : data)
            {
                out.append(p.toString() + "\n");
            }
        }
        return out.toString();
    }
}
