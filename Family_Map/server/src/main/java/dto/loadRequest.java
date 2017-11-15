package dto;

import models.event;
import models.person;
import models.user;

/**
 * The loadRequest class represents the body of Load. Passes in arrays of users, persons, and objects to be added to the database
 */

public class loadRequest {
    /**
     * Array of user objects to be added to the database
     */
    private user[] users;

    /**
     * Array of person objects to be added to the database
     */
    private person[] persons;

    /**
     * Array of event objects to be added to the database
     */
    private event[] events;

    /**
     * Default empty constructor
     */
    public loadRequest() {}

    /**
     * Class constructor that initializes class fields with the passed in parameters
     * @param users Array of User objects
     * @param persons Array of Person objects
     * @param events Array of Event objects
     */
    public loadRequest(user[] users, person[] persons, event[] events){

        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public user[] getUsers() {
        return users;
    }

    public void setUsers(user[] users) {
        this.users = users;
    }

    public person[] getPersons() {
        return persons;
    }

    public void setPersons(person[] persons) {
        this.persons = persons;
    }

    public event[] getEvents() {
        return events;
    }

    public void setEvents(event[] events) {
        this.events = events;
    }


}
