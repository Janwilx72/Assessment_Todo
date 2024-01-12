package com.jan.todo.core.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jan.todo.core.database.LocalDatabase;
import com.jan.todo.core.database.daos.TodoItemDao;
import com.jan.todo.core.database.entities.TodoItemEntity;

import java.util.List;

public class TodoRepository
{
    private TodoItemDao _dao;
    private LiveData<List<TodoItemEntity>> allTodoItemsLiveData;
    private LocalDatabase localDatabase;

    public TodoRepository(final Application application)
    {
        final LocalDatabase _localDatabase = LocalDatabase.getDatabase(application);
        _dao = _localDatabase.todoItemDao();
        allTodoItemsLiveData = _dao.getAll();
    }

    public LiveData<List<TodoItemEntity>> getAllTodoItemsLiveData()
    {
        return allTodoItemsLiveData;
    }

    public void insert(final TodoItemEntity entity)
    {
        LocalDatabase.databaseWriteExecutor.execute(() ->
        {
            _dao.insertAll(entity);
        });
    }

    public void update(final TodoItemEntity entity)
    {
        LocalDatabase.databaseWriteExecutor.execute(() ->
        {
            _dao.updateTodoItems(entity);
        });
    }

    public void delete(final TodoItemEntity entity)
    {
        LocalDatabase.databaseWriteExecutor.execute(() ->
        {
            _dao.delete(entity);
        });
    }
}
