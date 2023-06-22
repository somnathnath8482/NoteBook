package com.easy.myapplication.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.easy.myapplication.Adapter.NoteAdapter;
import com.easy.myapplication.Helper.IsAllFilled;
import com.easy.myapplication.Interface.AllInterface;
import com.easy.myapplication.R;
import com.easy.myapplication.Room.NoteRoom.Note;
import com.easy.myapplication.Room.NoteRoom.NoteViewModel;
import com.easy.myapplication.databinding.ActivityMainBinding;
import com.easy.myapplication.databinding.AddNoteBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    ActivityMainBinding binding;

    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new NoteViewModel(getApplication());

        binding.addNote.setOnClickListener(view -> {
            AddNote();
        });
        noteAdapter = new NoteAdapter(new DiffUtil.ItemCallback<Note>() {
            @Override
            public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getRid() == newItem.getRid();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getRid() == newItem.getRid()
                        && oldItem.getTitle().equalsIgnoreCase(newItem.getTitle())
                        && oldItem.getDescription().equalsIgnoreCase(newItem.getDescription())
                        ;
            }
        });
        binding.recycler.setAdapter(noteAdapter);
        noteViewModel.getAll().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });


        ItemTouchHelper.SimpleCallback call_tocach = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    Note note  = noteAdapter.getCurrentList().get(viewHolder.getAdapterPosition());
                    if (note!=null){
                        noteViewModel.delete(note.getRid()+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(call_tocach);
        itemTouchHelper.attachToRecyclerView(binding.recycler);

    }


    void AddNote() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(this).inflate(R.layout.add_note, null);
        dialog.setContentView(view);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        AddNoteBinding an = AddNoteBinding.bind(view);
        List<EditText> adds = new ArrayList<>();
        adds.add(an.title);
        adds.add(an.description);

        new IsAllFilled(adds).onFilled(new AllInterface() {
            @Override
            public void isClicked(boolean is) {
                super.isClicked(is);
                an.btnAddNote.setEnabled(is);
                an.btnAddNote.setClickable(is);
            }
        });

        an.btnAddNote.setOnClickListener(view1 -> {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            String date = utcFormat.format(new Date());

            noteViewModel.insert(new Note(an.title.getText().toString().trim(), an.description.getText().toString().trim(),
                    date, date));

            dialog.dismiss();
        });
    }
}