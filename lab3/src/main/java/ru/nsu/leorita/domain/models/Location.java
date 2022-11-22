package ru.nsu.leorita.domain.models;

public class Location {
    private String country;
    private String city;
    private String name;
    private String type;
    private String street;
    private String houseNumber;
    private Point point;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public String toString() {
        String result = "";
        if (name != null) {
            result += name;
        }
        if (country != null) {
            result += ", " + country;
        }
        if (city != null) {
            result += ", " + city;
        }
        if (type != null) {
            result += ", " + type;
        }
        if (street != null) {
            result += ", " + street;
        }
        if (houseNumber != null) {
            result += ", " + houseNumber;
        }
        return result;
    }

}
