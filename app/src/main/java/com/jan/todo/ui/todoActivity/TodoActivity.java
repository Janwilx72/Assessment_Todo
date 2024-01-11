package com.jan.todo.ui.todoActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jan.todo.R;
import com.jan.todo.core.dagger.components.DaggerTodoComponent;
import com.jan.todo.core.dagger.modules.TodoModule;
import com.jan.todo.core.database.entities.TodoItemEntity;
import com.jan.todo.core.database.repositories.TodoRepository;
import com.jan.todo.core.helpers.DateHelper;
import com.jan.todo.core.helpers.IconHelper;
import com.jan.todo.core.models.IconModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class TodoActivity extends AppCompatActivity
{
    @Inject
    public TodoRepository _todoRepository;
    @Inject public DateHelper _dateHelper;

    private SearchView _searchView;

    private TodoViewmodel _viewmodel;
    private TodoAdapter _adapter;

    private final List<TodoItemEntity> _todoList = new ArrayList<>();
    private List<TodoItemEntity> _filteredTodoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        DaggerTodoComponent
                .builder()
                .todoModule(new TodoModule(getApplication()))
                .build()
                .inject(this);

        _viewmodel = new ViewModelProvider(this).get(TodoViewmodel.class);
        _viewmodel.init(_todoRepository);

        initUi();
        initLiveData();
    }

    private void initUi()
    {
        _adapter = new TodoAdapter(_filteredTodoList);
        RecyclerView rvTodoList = findViewById(R.id.rvTodoList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        rvTodoList.setAdapter(_adapter);

        _searchView = findViewById(R.id.searchView);
        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                filterItems(s);

                return false;
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initLiveData()
    {
        _viewmodel.getTodoLiveData().observe(this, (list) ->
        {
            _todoList.clear();
            _todoList.addAll(list);

            filterItems(_searchView.getQuery().toString());
        });
    }

    private void filterItems(final String searchText)
    {
        _filteredTodoList.clear();

        List<TodoItemEntity> tempList = _todoList.stream()
                .filter(x -> x.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        _filteredTodoList.addAll(tempList);
        _adapter.notifyDataSetChanged();
    }

    class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>
    {
        private final List<TodoItemEntity> listdata;
        public TodoAdapter(List<TodoItemEntity> listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_todo, parent, false);
            return new ViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            final TodoItemEntity data = listdata.get(position);

            final IconModel icon = IconHelper.getIcons().stream().filter(x -> x.getIconId() == data.getIconId()).collect(Collectors.toList()).get(0);
            holder.ivIcon.setImageResource(icon.getResourceId());

            holder.tvTitle.setText(data.getTitle());
            holder.tvDescription.setText(data.getBody());
            holder.tvDueDate.setText(_dateHelper.convertLongToDate(data.getDueDate()));

            if (data.getDueDate() < System.currentTimeMillis())
            {
                holder.tvDueDate.setTextColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {
            return listdata == null ? 0 : listdata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ImageView ivIcon;
            public ImageView ivMenuSelector;
            public TextView tvTitle;
            public TextView tvDueDate;
            public TextView tvDescription;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                this.ivIcon = itemView.findViewById(R.id.ivIcon);
                this.ivMenuSelector = itemView.findViewById(R.id.ivMenuSelector);
                this.tvTitle = itemView.findViewById(R.id.tvTitle);
                this.tvDueDate = itemView.findViewById(R.id.tvDueDate);
                this.tvDescription = itemView.findViewById(R.id.tvDescription);
            }
        }
    }
}
