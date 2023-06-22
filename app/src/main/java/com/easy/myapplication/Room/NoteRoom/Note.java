package com.easy.myapplication.Room.NoteRoom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
* Created by Somnath nath on 22,June,2023
* Artix Development,
* India.
*/

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int rid;

    public String title, description,created_at,notify_at;

    public Note() {
    }

    public Note( String title, String description, String created_at, String notify_at) {
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.notify_at = notify_at;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getNotify_at() {
        return notify_at;
    }

    public void setNotify_at(String notify_at) {
        this.notify_at = notify_at;
    }
}
