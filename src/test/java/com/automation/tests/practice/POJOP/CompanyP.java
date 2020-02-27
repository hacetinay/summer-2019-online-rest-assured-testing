package com.automation.tests.practice.POJOP;

public class CompanyP {
    private AddressP addressP;
    private int companyId;
    private String companyName;
    private String startDate;
    private String title;

    public CompanyP() {
    }

    @Override
    public String toString() {
        return "CompanyP{" +
                "addressP=" + addressP +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public CompanyP(AddressP addressP, String companyName, String startDate, String title) {
        this.addressP = addressP;
        this.companyName = companyName;
        this.startDate = startDate;
        this.title = title;
    }

    public AddressP getAddressP() {
        return addressP;
    }

    public void setAddressP(AddressP addressP) {
        this.addressP = addressP;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
