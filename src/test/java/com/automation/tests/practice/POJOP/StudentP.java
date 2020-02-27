package com.automation.tests.practice.POJOP;

public class StudentP {
    private String admissionNo;
    private int batch;
    private String birthDate;
    private CompanyP companyP;
    private ContactP contactP;
    private String firstName;
    private String gender;
    private String joinDate;
    private String lastName;
    private String major;
    private String password;
    private String section;
    private int studentId;
    private String subject;

    public StudentP() {

    }

    @Override
    public String toString() {
        return "StudentP{" +
                "admissionNo='" + admissionNo + '\'' +
                ", batch=" + batch +
                ", birthDate='" + birthDate + '\'' +
                ", companyP=" + companyP +
                ", contactP=" + contactP +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", lastName='" + lastName + '\'' +
                ", major='" + major + '\'' +
                ", password='" + password + '\'' +
                ", section='" + section + '\'' +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                '}';
    }

    public StudentP(String admissionNo, int batch, String birthDate, CompanyP companyP, ContactP contactP, String firstName, String gender, String joinDate, String lastName, String major, String password, String section, String subject) {
        this.admissionNo = admissionNo;
        this.batch = batch;
        this.birthDate = birthDate;
        this.companyP = companyP;
        this.contactP = contactP;
        this.firstName = firstName;
        this.gender = gender;
        this.joinDate = joinDate;
        this.lastName = lastName;
        this.major = major;
        this.password = password;
        this.section = section;
        this.subject = subject;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public CompanyP getCompanyP() {
        return companyP;
    }

    public void setCompanyP(CompanyP companyP) {
        this.companyP = companyP;
    }

    public ContactP getContactP() {
        return contactP;
    }

    public void setContactP(ContactP contactP) {
        this.contactP = contactP;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
