package com.jan.todo.ui.todoActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jan.todo.core.database.entities.TodoItemEntity;
import com.jan.todo.core.database.repositories.TodoRepository;

import java.util.List;

public class TodoViewmodel extends ViewModel
{
    private TodoRepository _repository;

    private final LiveData<TodoItemEntity> updateLiveData = new MutableLiveData<>();

    public TodoViewmodel(){}

    public void init(final TodoRepository repository)
    {
        _repository = repository;
    }

    public LiveData<List<TodoItemEntity>> getTodoLiveData()
    {
        return _repository.getAllTodoItemsLiveData();
    }

    public LiveData<TodoItemEntity> getUpdateLiveData()
    {
        return updateLiveData;
    }

    public void insertTodoItem(final TodoItemEntity entity)
    {
        _repository.insert(entity);
    }

    public void deleteTodoItem(final TodoItemEntity entity)
    {
        _repository.delete(entity);
    }

    public void updateTodoItem(final TodoItemEntity entity)
    {
        _repository.update(entity);
    }


}
