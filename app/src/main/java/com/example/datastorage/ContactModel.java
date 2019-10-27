package com.example.datastorage;


import java.io.Serializable;

@SuppressWarnings("serial")
public class ContactModel implements Serializable {
    private String contactId;
    private String contactName;
    private String contactNo;
    private String contactEmail;
    private String contactPhoto;

    public ContactModel(String contactId, String contactName, String contactNo, String contactEmail, String contactPhoto) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNo = contactNo;
        this.contactEmail = contactEmail;
        this.contactPhoto = contactPhoto;
    }

    public ContactModel() {

    }

    public String getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(String contactPhoto) {
        this.contactPhoto = contactPhoto;
    }
}
