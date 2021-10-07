package com.example.devsu.my_ui;

/**
 * Created by MJ on 10/7/2021.
 */

public class STUDENT_INFO {

    private String FIRSTNAME;
    private String LASTNAME;
    private String MIDDLENAME;
    private String ID ;
    private String ROLE;
    private String BIRTHDATE;
    private String USERNAME;
    private String PASSWORD;
    private String EMAIL;
    private String CONTACT_NO;
    private String ADDRESS;

    public STUDENT_INFO(String FIRSTNAME, String LASTNAME, String MIDDLENAME, String ID, String ROLE, String BIRTHDATE, String USERNAME, String PASSWORD, String EMAIL, String CONTACT_NO, String ADDRESS) {
        this.FIRSTNAME = FIRSTNAME;
        this.LASTNAME = LASTNAME;
        this.MIDDLENAME = MIDDLENAME;
        this.ID = ID;
        this.ROLE = ROLE;
        this.BIRTHDATE = BIRTHDATE;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.EMAIL = EMAIL;
        this.CONTACT_NO = CONTACT_NO;
        this.ADDRESS = ADDRESS;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public String getMIDDLENAME() {
        return MIDDLENAME;
    }

    public void setMIDDLENAME(String MIDDLENAME) {
        this.MIDDLENAME = MIDDLENAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getBIRTHDATE() {
        return BIRTHDATE;
    }

    public void setBIRTHDATE(String BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getCONTACT_NO() {
        return CONTACT_NO;
    }

    public void setCONTACT_NO(String CONTACT_NO) {
        this.CONTACT_NO = CONTACT_NO;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
}
