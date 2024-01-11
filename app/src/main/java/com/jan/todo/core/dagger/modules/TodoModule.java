package com.jan.todo.core.dagger.modules;

import android.app.Application;

import com.jan.todo.core.database.repositories.TodoRepository;
import com.jan.todo.core.helpers.DateHelper;
import com.jan.todo.core.helpers.QRCodeHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TodoModule
{
    private Application _application;

    public TodoModule(){}

    public TodoModule(final Application application)
    {
        _application = application;
    }

    @Provides
    @Singleton
    public TodoRepository provideTodoRepository()
    {
        return new TodoRepository(_application);
    }

    @Provides
    public DateHelper provideDateHelper()
    {
        return new DateHelper();
    }

    @Provides
    public QRCodeHelper provideQRCodeHelper()
    {
        return new QRCodeHelper();
    }
}
