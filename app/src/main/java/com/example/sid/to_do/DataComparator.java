package com.example.sid.to_do;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by SID on 8/9/2017.
 */

public class DataComparator implements Comparator<Task_List> {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public int compare(Task_List taskList, Task_List t1) {
        String leftDate = taskList.getmTargetDate();
        String rightDate = t1.getmTargetDate();
        try{
            return dateFormat.parse(leftDate).compareTo(dateFormat.parse(rightDate));
        }
        catch (ParseException e){
            throw new IllegalArgumentException(e);
        }
    }
}
