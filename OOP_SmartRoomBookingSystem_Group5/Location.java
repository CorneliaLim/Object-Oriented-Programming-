public class Location {
    private String buildingName;
    private String floor;
    private String address;
    private String city;
    private String state;
    private String postalCode;

    public Location(String buildingName, String floor, String address, String city, String state, String postalCode) {
        this.buildingName = buildingName;
        this.floor = floor;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getFloor() {
        return floor;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String toString() {
        return String.format("%s, Floor %s\n%s\n%s, %s %s",
            buildingName, floor, address, city, state, postalCode);
    }
} 