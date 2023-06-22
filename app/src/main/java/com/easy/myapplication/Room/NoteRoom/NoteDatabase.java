package com.easy.myapplication.Room.NoteRoom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract  class NoteDatabase extends RoomDatabase {
  private static   NoteDatabase noteDatabase ;
    public abstract NoteDao  noteDao();
    public static synchronized NoteDatabase getInstance(Context context){
        if (noteDatabase== null){
            noteDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class,"NoteDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return noteDatabase;
        
    }
}
