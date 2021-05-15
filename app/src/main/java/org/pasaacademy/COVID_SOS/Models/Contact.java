package org.pasaacademy.COVID_SOS.Models;

public class Contact {

    private String contactName;
    private String contactNumber;
    private String location;

    public Contact() {

    }

    public Contact(String contactName, String contactNumber, String location) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.location =location;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
