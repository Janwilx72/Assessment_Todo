package com.jan.todo.ui.todoActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.jan.todo.ui.components.dialogs.ItemMenuDialog;
import com.jan.todo.ui.components.dialogs.SortDialog;
import com.jan.todo.ui.components.dialogs.TodoAddDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public void clickAddTodo(final View view)
    {
        clickViewTodoDialog(null);
    }

    public void clickViewTodoDialog(final TodoItemEntity entity)
    {
        final TodoAddDialog dialog = new TodoAddDialog(entity);
        dialog.setListener(new TodoAddDialog.TodoDialogListener()
        {
            @Override
            public void onCreateClick(final TodoItemEntity todoItem)
            {
                if (todoItem.getId() == 0)
                    _viewmodel.insertTodoItem(todoItem);
                else
                    _viewmodel.updateTodoItem(todoItem);

                dialog.dismiss();
            }

            @Override
            public void onCancelClick()
            {
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), "AddTodo");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.mnuSort)
        {
            openSortDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSortDialog()
    {
        final SortDialog dialog = new SortDialog();
        dialog.setListener(new SortDialog.SortDialogListener() {
            @Override
            public void SortClicked(boolean isAscending, String sortText)
            {
                sortItems(isAscending, sortText);
            }

            @Override
            public void SortByDefault()
            {
                _filteredTodoList.clear();
                _filteredTodoList.addAll(_todoList);
                _adapter.notifyDataSetChanged();
            }
        });
        dialog.show(getSupportFragmentManager(), "SortDialog");
    }

    private void sortItems(final boolean isAscending, final String sortText)
    {
        _filteredTodoList.sort((todo1, todo2) ->
        {
            final int todo1Count = countOccurrences(todo1.getBody(), todo1.getTitle(), sortText);
            final int todo2Count = countOccurrences(todo2.getBody(),todo2.getTitle(), sortText);

            // Compare based on the specified order
            return !isAscending
                    ? Integer.compare(todo1Count, todo2Count)
                    : Integer.compare(todo2Count, todo1Count);
        });

        _adapter.notifyDataSetChanged();
    }

    private static int countOccurrences(final String body, final String title, final String sortText)
    {
        final String fullText = body.concat(" ").concat(title);

        final Pattern pattern = Pattern.compile("\\b" + Pattern.quote(sortText) + "\\b", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(fullText);

        int count = 0;
        while (matcher.find())
        {
            count++;
        }

        return count;
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

            holder.ivMenuSelector.setOnClickListener(view ->
            {
                final ItemMenuDialog dialog = new ItemMenuDialog(data);
                dialog.setListener(new ItemMenuDialog.ItemMenuDialogListener()
                {
                    @Override
                    public void onEditClick(TodoItemEntity itemEntity)
                    {
                        clickViewTodoDialog(itemEntity);
                        dialog.dismiss();
                    }

                    @Override
                    public void onDeleteClick(TodoItemEntity itemEntity)
                    {
                        _viewmodel.deleteTodoItem(itemEntity);
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), "ItemMenuDialog");
            });
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
