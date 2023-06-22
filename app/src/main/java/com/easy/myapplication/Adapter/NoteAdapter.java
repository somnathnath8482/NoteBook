package com.easy.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.easy.myapplication.Activity.NoteDetails;
import com.easy.myapplication.R;
import com.easy.myapplication.Room.NoteRoom.Note;
import com.easy.myapplication.databinding.NoteBinding;

/**
 * Created by Somnath nath on 22,June,2023
 * Artix Development,
 * India.
 */
public class NoteAdapter extends ListAdapter<Note,NoteAdapter.ViewHolder> {
Context context ;
    public NoteAdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context  = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.note,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteBinding binding =NoteBinding.bind(holder.view);
        Note item = getItem(position);
        binding.title.setText(item.getTitle());
        binding.desc.setText(item.getDescription());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NoteDetails.class);
            intent.putExtra("id", item.getRid()+"");
            context.startActivity(intent);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view  =itemView;
        }
    }
}
