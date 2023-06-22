package com.easy.myapplication.Room.NoteRoom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class NoteRepository {
    NoteDao noteDao;
    private LiveData<List<Note>> all_note;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        all_note = noteDao.getAll();
    }


    public void insert(Note note) {
        new InserAsync(noteDao).execute(note);

    }

    public void update(Note note) {
        new updateNoteAsync(noteDao).execute(note);

    }


    public void delete(String rid) {
        new DeleteAsync(noteDao).execute(rid);


    }

    public Note getNote(String rid) {
        Note note = null;

        try {
            note = new getNoteAsync(noteDao).execute(rid).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;

    }

    public void deleteAll() {
        new DeleteAllAsync(noteDao).execute();
    }

    public LiveData<List<Note>> getAll() {
        return all_note;
    }


    private static class InserAsync extends AsyncTask<Note, Void, Void> {
        private final NoteDao noteDao;

        private InserAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... users) {
            try {
                noteDao.insert(users[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private static class DeleteAsync extends AsyncTask<String, Void, Void> {
        private final NoteDao noteDao;

        private DeleteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            noteDao.delete(strings[0]);
            return null;
        }
    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void> {
        private final NoteDao noteDao;

        private DeleteAllAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }

    private static class getNoteAsync extends AsyncTask<String, Void, Note> {
        private final NoteDao noteDao;

        private getNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Note doInBackground(String... strings) {
            return noteDao.getNote(strings[0]);
        }
    }

    private static class updateNoteAsync extends AsyncTask<Note, Void, Void> {
        private final NoteDao noteDao;

        private updateNoteAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            Note note = notes[0];
            noteDao.update(note.getRid(), note.getTitle(), note.getDescription());
            return null;
        }
    }


}
