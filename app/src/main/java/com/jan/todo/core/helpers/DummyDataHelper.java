package com.jan.todo.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.jan.todo.core.database.entities.TodoItemEntity;

import java.util.ArrayList;
import java.util.List;

public class DummyDataHelper
{
    private final String PREFS_NAME = "Preferences";
    private final String IS_INITIAL_LOAD = "isInitialLoad";

    private final List<TodoItemEntity> _dummyDataList = new ArrayList<>();

    public List<TodoItemEntity> getDummyData()
    {
        final TodoItemEntity entity1 = new TodoItemEntity();
        entity1.setTitle("Chores");
        entity1.setBody("Do my household chores. Wash clothing, do the dishes and mop the floors");
        entity1.setDueDate(System.currentTimeMillis() + 1000000000L);
        entity1.setIconId(1);

        final TodoItemEntity entity2 = new TodoItemEntity();
        entity2.setTitle("Organise Birthday Party");
        entity2.setBody("Organise my birthday party destination and guests");
        entity2.setDueDate(System.currentTimeMillis() + 1100000000L);
        entity2.setIconId(4);

        final TodoItemEntity entity3 = new TodoItemEntity();
        entity3.setTitle("Practise Guitar");
        entity3.setBody("Learn a new song");
        entity3.setDueDate(System.currentTimeMillis() - 1000);
        entity3.setIconId(2);

        _dummyDataList.add(entity1);
        _dummyDataList.add(entity2);
        _dummyDataList.add(entity3);

        return _dummyDataList;
    }

    public boolean hasSetupDummyData(final Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isInitial = prefs.getBoolean(IS_INITIAL_LOAD, true);

        if (isInitial)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(IS_INITIAL_LOAD, false);
            editor.apply();
        }

        return !isInitial;
    }
}
