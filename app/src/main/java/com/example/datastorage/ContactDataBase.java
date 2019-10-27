package com.example.datastorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactTable.class},version = 2,exportSchema = false)
public abstract class ContactDataBase extends RoomDatabase
{
    public abstract Dao MyDao();

}
