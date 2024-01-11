package com.jan.todo.core.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TodoItems")
public class TodoItemEntity
{
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String body;
    public int iconId;
    public long dueDate;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(final int iconId) {
        this.iconId = iconId;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(final long dueDate) {
        this.dueDate = dueDate;
    }
}
