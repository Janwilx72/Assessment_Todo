package com.jan.todo.core.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jan.todo.core.database.entities.TodoItemEntity;

import java.util.List;

@Dao
public interface TodoItemDao
{
    @Query("SELECT * FROM TodoItems")
    LiveData<List<TodoItemEntity>> getAll();

    @Insert
    void insertAll(final TodoItemEntity... items);

    @Delete
    void delete(final TodoItemEntity entity);

    @Update
    void updateTodoItems(final TodoItemEntity... items);
}
