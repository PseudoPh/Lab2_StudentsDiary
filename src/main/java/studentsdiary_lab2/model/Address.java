package studentsdiary_lab2.model;

public record Address(String city, String street, String building) {

    @Override
    public String toString() {
        return city + ", " + street + ", " + building;
    }
}