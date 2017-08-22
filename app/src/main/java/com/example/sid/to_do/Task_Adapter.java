package com.example.sid.to_do;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SID on 7/24/2017.
 */

public class Task_Adapter extends BaseAdapter {

    private ArrayList<Task_List> mTaskArrayList;
    private Context mContext;
    private LayoutInflater mlayoutInflater;

    public Task_Adapter(Context mContext,ArrayList<Task_List> mTaskArrayList){
        this.mContext = mContext;
        this.mTaskArrayList = mTaskArrayList;
        mlayoutInflater = LayoutInflater.from(mContext);
    }

    public void setTaskList(ArrayList<Task_List> mTaskArrayList){
        this.mTaskArrayList=mTaskArrayList;

    }



    @Override
    public int getCount() {
        return mTaskArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mTaskArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mlayoutInflater.inflate(R.layout.task_list,viewGroup,false);

        TextView mTitle = (TextView) view.findViewById(R.id.tvTaskTitle);
        TextView mDescription = (TextView) view.findViewById(R.id.tvTaskDescription);
        TextView mTargetDate = (TextView) view.findViewById(R.id.tvTargetDate);
        TextView mTaskTitle = (TextView) view.findViewById(R.id.tvDateTitle);
        ImageView mImageView = (ImageView) view.findViewById(R.id.ivIncompleteTask);

        Task_List task_list = mTaskArrayList.get(i);
        mTaskTitle.setText(task_list.getmTargetDate());
        mTitle.setText(task_list.getmTitle());
        mDescription.setText(task_list.getmDescription());
        mTargetDate.setText(task_list.getmTargetDate());

        if (task_list.getmStatus()==1)
            mImageView.setImageResource(R.drawable.complete);
        else
            mImageView.setImageResource(R.drawable.incomplete);
        return view;
    }
}
