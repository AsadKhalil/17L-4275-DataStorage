package com.example.datastorage;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao
{
    @Insert
    public void addContact(ContactTable contactTable);

    @Query("select * from contactTable")
    public List<ContactTable> getContacts();

    @Delete
    public void deleteContact(ContactTable contactTable);

}
