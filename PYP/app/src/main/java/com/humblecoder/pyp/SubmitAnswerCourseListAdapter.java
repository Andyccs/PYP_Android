package com.humblecoder.pyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.humblecoder.pyp.model.Course;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Andy on 10/1/2014.
 */
public class SubmitAnswerCourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courses;

    public SubmitAnswerCourseListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return courses!=null?courses.size():0;
    }

    @Override
    public Object getItem(int i) {
        return courses!=null?courses.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return courses!=null?courses.get(i).getObjectId().hashCode():-1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder temp;
        if(view==null) {
            temp = new ViewHolder();
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,null);
            temp.text = (TextView)view;
            view.setTag(temp);
        }
        else {
            temp = (ViewHolder) view.getTag();
        }
        temp.text.setText(courses.get(i).getCourseTitle());

        return temp.text;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView text;
    }
}
