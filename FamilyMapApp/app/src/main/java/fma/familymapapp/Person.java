package fma.familymapapp;


public class Person {
    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    //private boolean valid;
    public Person() {
    }

    public Person(String descendant, String personId, String first, String last, String gen, String pa, String ma, String spouse)
    {
        this.descendant = descendant;
        this.personID = personId;
        this.firstName = first;
        this.lastName = last;
        this.gender = gen;
        this.father = pa;
        this.mother = ma;
        this.spouse = spouse;
    }

    public boolean equals(Person p)
    {
        return this.descendant.equals(p.descendant) &&
                this.personID.equals(p.personID) &&
                this.firstName.equals(p.firstName) &&
                this.lastName.equals(p.lastName) &&
                this.gender.equals(p.gender) &&
                this.father.equals(p.father) &&
                this.mother.equals(p.mother) &&
                this.spouse.equals(p.spouse);
    }


    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String toString()
    {
        StringBuilder out = new StringBuilder();
        out.append("descendant: " + descendant + "\npersonID: " + personID + "\nfirstName: "
                + firstName + "\nlastName: " + lastName + "\ngender: " + gender + "\nfather: "
                + father + "\nmother: " + mother + "\nspouse: " + spouse);
        return out.toString();
    }
}
