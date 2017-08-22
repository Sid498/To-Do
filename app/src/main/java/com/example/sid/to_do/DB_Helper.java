package com.example.sid.to_do;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SID on 7/24/2017.
 */

public class DB_Helper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private static final String DB_NAME = "TODO";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";

    public DB_Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating database and table to add tasks
        String CREATE_TABLE = " create table " + TABLE_NAME + "("
                + KEY_ID + " integer primary key, "
                + KEY_TITLE + " text, "
                + KEY_DESCRIPTION + " text, "
                + KEY_DATE + " text, "
                + KEY_STATUS + " integer "
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void onInsert(String title, String description, String date, int status) {
        //inserting task
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_STATUS, status);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<Task_List> getAllCompltedTask() {
        //this method will return all completed task
        ArrayList<Task_List> task_lists = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String select = "select * from " + TABLE_NAME + " where " + KEY_STATUS + " = 1 ";
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                Task_List tasklist = new Task_List();
                tasklist.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                tasklist.setmTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                tasklist.setmDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                tasklist.setmTargetDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                tasklist.setmStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));

                task_lists.add(tasklist);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return task_lists;
    }

    public ArrayList<Task_List> getAllTasks() {
        //this method will return all task
        //completed and incompleted
        ArrayList<Task_List> taskList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task_List task_list = new Task_List();
                task_list.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                task_list.setmTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                task_list.setmDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                task_list.setmTargetDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                task_list.setmStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));

                taskList.add(task_list);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return taskList;
    }

    public int updateTaskStatus(int whereID, int status) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, status);

        int count = sqLiteDatabase.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(whereID)});
        sqLiteDatabase.close();
        return count;
    }

    public int updateTask(Task_List task_list, String title, String description, String targetDate) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_DATE, targetDate);

        int count = sqLiteDatabase.update(TABLE_NAME, contentValues, KEY_ID + "=?", new String[]{String.valueOf(task_list.getmId())});
        sqLiteDatabase.close();
        return count;
    }

    public boolean deleteTask(int Id) {
        //for deleting task
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(Id)});
        sqLiteDatabase.close();
        return true;
    }
}
