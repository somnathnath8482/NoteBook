package com.easy.myapplication.Room.NoteRoom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;



import java.util.List;


@Dao
public interface NoteDao {


    @Insert
    void insert(Note note);

    @Query("UPDATE Note set title=:title,description=:desc where rid=:id")
    void update(int id,String title, String desc);

    @Query("SELECT * FROM Note WHERE rid=:id")
    Note getNote(String id);

    @Query("DELETE  FROM note WHERE rid =:id ")
    void delete(String id);

    @Query("DELETE FROM note")
    void deleteAll();

    @Query("SELECT * FROM note ORDER BY created_at ")
    LiveData<List<Note>> getAll();



}
