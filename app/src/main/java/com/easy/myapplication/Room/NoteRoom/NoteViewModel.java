package com.easy.myapplication.Room.NoteRoom;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository noteRepository;
    private LiveData<List<Note>> all_note;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        all_note = noteRepository.getAll();
    }

    public LiveData<List<Note>> getAll() {
        return all_note;
    }
    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void delete(String rid) {
         noteRepository.delete(rid);
    }

    public void update(Note note) {
         noteRepository.update(note);
    }
 public Note getNote(String id) {
       return   noteRepository.getNote(id);
    }

    public void deleteAll() {
         noteRepository.deleteAll();
    }
}
