package com.jan.todo.core.dagger.components;

import javax.inject.Singleton;

import com.jan.todo.core.dagger.modules.TodoModule;
import com.jan.todo.ui.todoActivity.TodoActivity;
import com.jan.todo.ui.todoActivity.TodoViewmodel;

import dagger.Component;

@Singleton
@Component(modules = {TodoModule.class})
public interface TodoComponent
{
    void inject(final TodoActivity activity);
    void inject(final TodoViewmodel viewmodel);

    @Component.Builder
    interface Builder
    {
        Builder todoModule(final TodoModule module);
        TodoComponent build();
    }
}
