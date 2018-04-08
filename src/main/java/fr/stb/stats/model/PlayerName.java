package fr.stb.stats.model;

public class PlayerName {

    private String firstName;

    private String lastName;

    public PlayerName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerName that = (PlayerName) o;
        return this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        int result = 29;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlayerName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String asString() {
        return firstName + " " + lastName;
    }
}
