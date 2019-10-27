package com.example.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactTable")
public class ContactTable {

    @PrimaryKey
    private int id;

    private String name;
    private  String number;
    private  String email;

    private byte[] image;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
