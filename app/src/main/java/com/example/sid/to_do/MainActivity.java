package com.example.sid.to_do;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.example.sid.to_do.R.id.parent;
import static com.example.sid.to_do.R.id.tvDateTitle;
import static com.example.sid.to_do.R.id.tvTargetDate;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnFocusChangeListener {

    ListView mlistView;
    ArrayList<Task_List> mTaskList;
    Task_Adapter task_adapter;

    LayoutInflater layoutInflater;
    DB_Helper dbHelper;

    protected void onResume() {
        super.onResume();
        refreshListContents();
    }

    private void refreshListContents() {
        //creating this method because if user
        // deleted or updated the task the method will make changes
        mTaskList = dbHelper.getAllTasks();
        Collections.sort(mTaskList, new DataComparator());
        task_adapter.setTaskList(mTaskList);
        task_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutInflater = LayoutInflater.from(this);
        dbHelper = new DB_Helper(this);

        mlistView = (ListView) findViewById(R.id.listview);
        mTaskList = dbHelper.getAllTasks();
        Collections.sort(mTaskList, new DataComparator());
        task_adapter = new Task_Adapter(this, mTaskList);

        mlistView.setAdapter(task_adapter);
        mlistView.setOnItemClickListener(this);
        mlistView.setOnItemLongClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newTask: {
                View view = layoutInflater.inflate(R.layout.task_entry, null);
                view.findViewById(R.id.etTastTitle).setOnFocusChangeListener(this);
                AlertDialog.Builder builder = createAlertDialog(view);
                builder.show();
                return true;
            }

            case R.id.completedTask: {
                Intent intent = new Intent(MainActivity.this, CompletedTask.class);
                startActivity(intent);
                Toast.makeText(this, "Completed Task View", Toast.LENGTH_SHORT).show();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog.Builder createAlertDialog(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton(getResources().getString(R.string.dialogPosBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the inputs from this and insert it into the DB
                EditText titleTxt = (EditText) view.findViewById(R.id.etTastTitle);
                String title = titleTxt.getText().toString().trim();
                EditText descTxt = (EditText) view.findViewById(R.id.etTastDescription);
                String description = descTxt.getText().toString();
                DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepickerTask);
                String targetDate = String.format("%02d", datePicker.getDayOfMonth()) + "/" +
                        String.format("%02d", Integer.valueOf(datePicker.getMonth() + 1)) + "/" + datePicker.getYear();

                if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Task not included", Toast.LENGTH_SHORT).show();
                    return;
                }

                // To insert a row in DB
                dbHelper.onInsert(title, description, targetDate, 0);

                // To notify the ListView about the dataset change
                MainActivity.this.refreshListContents();

                Toast.makeText(MainActivity.this, "Title : " + title + "\nDescription : " + description + "\nTargetDate : " + targetDate, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.dialogNegBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(TaskActivity.this, "Cancel button clicked", Toast.LENGTH_LONG).show();
            }
        });
        builder.setCancelable(false);
        return builder;

    }

    private AlertDialog.Builder editTaskDialog(final View view, final Task_List theTask) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        mBuilder.setView(view);
        mBuilder.setPositiveButton(getResources().getString(R.string.dialogPosBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the inputs from this and insert it into the DB
                EditText titleTxt = (EditText) view.findViewById(R.id.etTastTitle);
                String title = titleTxt.getText().toString().trim();
                EditText descTxt = (EditText) view.findViewById(R.id.etTastDescription);
                String description = descTxt.getText().toString();
                DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepickerTask);
                String targetDate = String.format("%02d", datePicker.getDayOfMonth()) + "/" +
                        String.format("%02d", Integer.valueOf(datePicker.getMonth() + 1)) + "/" + datePicker.getYear();

                if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Task not updated!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // To insert a row in DB
                dbHelper.updateTask(theTask, title, description, targetDate);

                // To notify the ListView about the dataset change
                MainActivity.this.refreshListContents();

                Toast.makeText(MainActivity.this, "Title : " + title + "\nDescription : " + description + "\nTargetDate : " + targetDate, Toast.LENGTH_LONG).show();
            }
        });
        mBuilder.setNegativeButton(getResources().getString(R.string.dialogNegBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(TaskActivity.this, "Cancel button clicked", Toast.LENGTH_LONG).show();
            }
        });
        mBuilder.setCancelable(false);
        return mBuilder;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.etTastTitle) {
            EditText title = (EditText) view;
            if (title.getText().length() < 1) {
                title.setError("Please Enter Title");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Task_List currentTask = (Task_List) adapterView.getAdapter().getItem(i);
        View dialogView = layoutInflater.inflate(R.layout.task_entry, null);

        EditText txtTitle = (EditText) dialogView.findViewById(R.id.etTastTitle);
        EditText txtDescription = (EditText) dialogView.findViewById(R.id.etTastDescription);
        DatePicker targetDate = (DatePicker) dialogView.findViewById(R.id.datepickerTask);

        txtTitle.setOnFocusChangeListener(this);

        txtTitle.setText(currentTask.getmTitle());
        txtDescription.setText(currentTask.getmDescription());
        String[] dateArray = currentTask.getmTargetDate().split("/");
        targetDate.init(Integer.valueOf(dateArray[2]),
                Integer.valueOf(dateArray[1]) - 1,
                Integer.valueOf(dateArray[0]),
                null);

        AlertDialog.Builder builder = editTaskDialog(dialogView, currentTask);
        builder.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //to update the task when user long click on task
        Task_List task_list = (Task_List) adapterView.getAdapter().getItem(i);
        if (task_list.getmStatus() == 1) {

            return true;
        }
        int updateCount = dbHelper.updateTaskStatus(task_list.getmId(), 1);
        refreshListContents();
        Toast.makeText(this, "Task Complated..!! " + task_list.getmTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}

