package com.humblecoder.pyp;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humblecoder.pyp.models.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andy on 9/22/2014.
 */
@TargetApi(21)
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView view;

        @InjectView(R.id.course_code)
        TextView courseCode;

        @InjectView(R.id.course_name)
        TextView courseName;

        public ViewHolder(CardView v) {
            super(v);
            view = v;
            ButterKnife.inject(this, v);
        }
    }

    private Context context;

    private List<Course> courses;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseListAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        v.setBackground(context.getResources().getDrawable(R.drawable.bg_course_item_selector));


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position]);
        holder.courseCode.setText(courses.get(position).getCourseCode());
        holder.courseName.setText(courses.get(position).getCourseTitle());


    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

//        context.getResources().getAnimation(android.R.interpolator.fast_out_slow_in);
//        @interpolator/fast_out_slow_in.xml
//        ObjectAnimator mAnimator;
//        mAnimator = ObjectAnimator.ofFloat(holder.view, View.X, View.Y, path);
//        mAnimator.start();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return mDataset.length;
        return courses!=null?courses.size():0;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void addCourse(Course course){
        if(this.courses == null){
            courses = new ArrayList<Course>();
        }
        this.courses.add(course);
        notifyItemInserted(courses.size()-1);
    }
}