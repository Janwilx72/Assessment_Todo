package com.jan.todo.ui.todoActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jan.todo.R;
import com.jan.todo.core.database.entities.TodoItemEntity;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity
{
    private TodoViewmodel _viewmodel;

    private final List<TodoItemEntity> _todoList = new ArrayList<>();
    private List<TodoItemEntity> _filteredTodoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        _viewmodel = new ViewModelProvider(this).get(TodoViewmodel.class);
    }
}
