package dto;

import models.event;

/**
 * The allEventsResponse class returns ALL events for ALL family members of the current user
 */
public class allEventsResponse {
    /**
     * Array of event objects
     */
    private event[] data;
    //private eventResponse[] data;

    /**
     * String containing a message to describe error
     */
    private String message;

    //json object?

    /**
     * Default empty constructor
     */
    public allEventsResponse(){}

    public event[] getData() {
        return data;
    }

    public void setData(event[] data) {
        this.data = data.clone();
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
            for(event e : data)
            {
                out.append(e.toString() + "\n");
            }
        }
        return out.toString();
    }
}
