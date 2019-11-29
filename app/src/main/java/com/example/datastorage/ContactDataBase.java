package com.example.datastorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactTable.class},version = 3,exportSchema = false)
public abstract class ContactDataBase extends RoomDatabase
{
    public abstract Dao MyDao();

}
