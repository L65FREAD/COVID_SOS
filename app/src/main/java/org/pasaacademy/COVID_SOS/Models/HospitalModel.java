package org.pasaacademy.COVID_SOS.Models;

/**
 * Model class for the hospital database
 */
public class HospitalModel {
    private String hospitalName, hospitalAddress, contactNumber, lastUpdated, location;
    private long availableBeds, icuAvailable, ventilatorAvailable;
    boolean acceptingPatients,oxygenAvailable;
    public HospitalModel(){

    }

    public HospitalModel(String hospitalName, String hospitalAddress, String contactNumber, long availableBeds, boolean acceptingPatients, boolean oxygenAvailable, String lastUpdated, String location,long icuAvailable, long ventilatorAvailable) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.contactNumber = contactNumber;
        this.availableBeds = availableBeds;
        this.acceptingPatients = acceptingPatients;
        this.oxygenAvailable = oxygenAvailable ;
        this.lastUpdated = lastUpdated;
        this.location = location;
        this.icuAvailable = icuAvailable;
        this.ventilatorAvailable = ventilatorAvailable;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public long getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(long availableBeds) {
        this.availableBeds = availableBeds;
    }

    public boolean getAcceptingPatients() {
        return acceptingPatients;
    }

    public void setAcceptingPatients(boolean acceptingPatients) {
        this.acceptingPatients = acceptingPatients;
    }

    public boolean getOxygenAvailable() {
        return oxygenAvailable;
    }

    public void setOxygenAvailable(boolean oxygenAvailable) {
        this.oxygenAvailable = oxygenAvailable;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getIcuAvailable() {
        return icuAvailable;
    }

    public void setIcuAvailable(long icuAvailable) {
        this.icuAvailable = icuAvailable;
    }

    public long getVentilatorAvailable() {
        return ventilatorAvailable;
    }

    public void setVentilatorAvailable(long ventilatorAvailable) {
        this.ventilatorAvailable = ventilatorAvailable;
    }
}
