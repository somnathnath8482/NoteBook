package com.easy.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.easy.myapplication.R;
import com.easy.myapplication.Room.NoteRoom.Note;
import com.easy.myapplication.Room.NoteRoom.NoteViewModel;
import com.easy.myapplication.databinding.ActivityNoteDetailsBinding;

public class NoteDetails extends AppCompatActivity {
ActivityNoteDetailsBinding binding;
NoteViewModel noteViewModel;
Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new NoteViewModel(getApplication());
        String id = getIntent().getStringExtra("id");
        id  = id==null?"":id;
      note  = noteViewModel.getNote(id);

       if (note!=null){
           binding.title.setText(note.getTitle());
           binding.description.setText(note.getDescription());
       }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (note!=null){
            noteViewModel.update(note);
        }
    }
}