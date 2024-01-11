package com.jan.todo.ui.todoActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jan.todo.core.database.entities.TodoItemEntity;

public class TodoViewmodel extends ViewModel
{
    private final LiveData<TodoItemEntity> updateLiveData = new MutableLiveData<>();

    public TodoViewmodel(){}

    public LiveData<TodoItemEntity> getUpdateLiveData()
    {
        return updateLiveData;
    }

}
