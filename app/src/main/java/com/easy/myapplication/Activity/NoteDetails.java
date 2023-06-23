package com.easy.myapplication.Activity;

import static android.graphics.Color.TRANSPARENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.easy.myapplication.Helper.Alarm;
import com.easy.myapplication.Helper.IsAllFilled;
import com.easy.myapplication.Helper.MethodClass;
import com.easy.myapplication.Interface.AllInterface;
import com.easy.myapplication.Interface.OnDateSelect;
import com.easy.myapplication.R;
import com.easy.myapplication.Room.NoteRoom.Note;
import com.easy.myapplication.Room.NoteRoom.NoteViewModel;
import com.easy.myapplication.databinding.ActivityNoteDetailsBinding;
import com.easy.myapplication.databinding.AddNoteBinding;
import com.easy.myapplication.databinding.SetAlermBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteDetails extends AppCompatActivity {
ActivityNoteDetailsBinding binding;
NoteViewModel noteViewModel;
Note note;
   static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new NoteViewModel(getApplication());
         id = getIntent().getStringExtra("id");
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
        if (item.getItemId() == R.id.delete){
            noteViewModel.delete(id);
            finish();
        }
        else if  (item.getItemId() == R.id.notify){

            setAlerm(this);

        }
        return super.onOptionsItemSelected(item);
    }

    public void setAlerm(Activity activity) {

        BottomSheetDialog dialog = new BottomSheetDialog(activity, R.style.SheetDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_alerm, null);

        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        dialog.show();

        SetAlermBinding binding = SetAlermBinding.bind(dialogView);
        final Calendar max = Calendar.getInstance();
        final Calendar min = Calendar.getInstance();
        final Calendar[] date = new Calendar[1];
        date[0] = Calendar.getInstance();
        max.add(Calendar.YEAR, +100);
        binding.date.setOnClickListener(view -> MethodClass.GetSelectdDate(new OnDateSelect() {
            @Override
            public void OnSelect(String dae) {
                binding.date.setText(dae);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {

                    date[0].setTime(inputFormat.parse(dae));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                activity,max.getTimeInMillis(),min.getTimeInMillis() ));
        binding.time.setOnClickListener(view -> {
            MethodClass.getTime(activity,min, new AllInterface(){
                @Override
                public void OnSuccess(int... ints) {
                    super.OnSuccess(ints);
                    binding.time.setText(ints[0]+":"+ints[1]);
                    date[0].set(Calendar.HOUR_OF_DAY,ints[0] );
                    date[0].set(Calendar.MINUTE,ints[1]);
                }
            });
        });


        List<EditText> adds = new ArrayList<>();
        adds.add(binding.time);
        adds.add(binding.date);

        new IsAllFilled(adds).onFilled(new AllInterface() {
            @Override
            public void isClicked(boolean is) {
                super.isClicked(is);
                binding.add.setEnabled(is);
                binding.add.setClickable(is);
            }
        });

        binding.add.setOnClickListener(view -> {

            Intent activate = new Intent(activity, Alarm.class);
            activate.putExtra("id", id);
            activate.putExtra("title", NoteDetails.this.binding.title.getText().toString().trim());
            activate.putExtra("desc", NoteDetails.this.binding.description.getText().toString().trim());
            AlarmManager alarms ;
            PendingIntent alarmIntent = PendingIntent.getBroadcast(activity, Integer.parseInt(id), activate, 0);

            alarms = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

            alarms.cancel(alarmIntent);
            alarms.set(AlarmManager.RTC_WAKEUP, date[0].getTimeInMillis(), alarmIntent);
            dialog.dismiss();
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        if (note!=null){
            note.setTitle(binding.title.getText().toString().trim());
            note.setDescription(binding.description.getText().toString().trim());
            noteViewModel.update(note);
        }
    }
}