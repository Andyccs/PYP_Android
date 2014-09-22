package com.humblecoder.pyp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ResourceBundle;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andy on 9/22/2014.
 */
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
        holder.courseCode.setText("CZ3002");
        holder.courseName.setText("Advance Software engineering");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return mDataset.length;
        return 10;
    }
}