package com.example.sid.to_do;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SID on 8/9/2017.
 */

public class CompletedTask extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView mListView;
    Button btnAdd;

    ArrayList<Task_List> mTaskList;
    Task_Adapter taskAdapter;
    LayoutInflater layoutInflater;
    Task_List task_list;
    DB_Helper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutInflater = LayoutInflater.from(this);
        dbHelper = new DB_Helper(this);
        mListView = (ListView) findViewById(R.id.listview);
        mTaskList = dbHelper.getAllCompltedTask();
        Collections.sort(mTaskList, new DataComparator());
        taskAdapter = new Task_Adapter(this, mTaskList);
        mListView.setAdapter(taskAdapter);
        mListView.setOnItemLongClickListener(this);
    }

    private void refreshListViewContents() {
        //refreshing listview
        mTaskList = dbHelper.getAllCompltedTask();
        taskAdapter.setTaskList(mTaskList);
        taskAdapter.notifyDataSetChanged();
    }

    /*
    implemented both.. context menu for deleting and on long
    click also we can delete task.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        task_list = (Task_List) adapterView.getAdapter().getItem(i);
        //  boolean isDeleted = dbHelper.deleteTask(task_list.getmId());
        registerForContextMenu(adapterView);
        openContextMenu(adapterView);
        //if (isDeleted){
        //refreshListViewContents();
        //  Toast.makeText(this,"Removed the completed task :"+task_list.getmTitle(),Toast.LENGTH_SHORT).show();
        //}
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        if (view.getId() == R.id.listview) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu, menu);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                dbHelper.deleteTask(task_list.getmId());
                refreshListViewContents();
                Toast.makeText(this, "Removed the completed task : " + task_list.getmTitle(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
