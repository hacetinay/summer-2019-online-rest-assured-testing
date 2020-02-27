package com.automation.tests.practice.POJOP;

import com.google.gson.annotations.SerializedName;

public class ContactP {
    private int  contactId;
    private String emailAddress;
    private String phone;
    @SerializedName("permanentAddress")
    private String premanentAddress;

    public ContactP() {
    }

    @Override
    public String toString() {
        return "ContactP{" +
                "contactId=" + contactId +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", premanentAddress='" + premanentAddress + '\'' +
                '}';
    }

    public ContactP(String emailAddress, String phone, String premanentAddress) {
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.premanentAddress = premanentAddress;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPremanentAddress() {
        return premanentAddress;
    }

    public void setPremanentAddress(String premanentAddress) {
        this.premanentAddress = premanentAddress;
    }
}
