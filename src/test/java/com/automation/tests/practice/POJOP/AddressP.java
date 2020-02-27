package com.automation.tests.practice.POJOP;

public class AddressP {
    private int addressId;
    private String city;
    private String state;
    private String street;
    private int zipCode;

    @Override
    public String toString() {
        return "AddressP{" +
                "addressId=" + addressId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

    public AddressP(String city, String state, String street, int zipCode) {
        this.city = city;
        this.state = state;
        this.street = street;
        this.zipCode = zipCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public AddressP() {
    }

}