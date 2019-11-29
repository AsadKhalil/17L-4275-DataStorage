package com.example.datastorage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class ContactTable {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;


    private  String number;
    private  String email;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;



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

    public ContactTable(String id, String name, String number, String email, byte[] image) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
