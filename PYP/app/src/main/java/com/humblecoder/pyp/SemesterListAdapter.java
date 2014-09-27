package com.humblecoder.pyp;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humblecoder.pyp.model.Course;
import com.humblecoder.pyp.model.Paper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andy on 9/22/2014.
 */
@TargetApi(21)
public class SemesterListAdapter extends RecyclerView.Adapter<SemesterListAdapter.ViewHolder> {

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView view;

        @InjectView(R.id.semester)
        TextView semester;

        public ViewHolder(CardView v) {
            super(v);
            view = v;
            ButterKnife.inject(this, v);
        }
    }

    private Context context;

    private List<Paper> papers;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SemesterListAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SemesterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.semester_list_item, parent, false);

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
        holder.semester.setText(papers.get(position).getAcademicYear()+ " Semester " + papers.get(position).getSemester());
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return mDataset.length;
        return papers !=null? papers.size():0;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
        notifyDataSetChanged();
    }

    public void addCourse(Paper course){
        if(this.papers == null){
            papers = new ArrayList<Paper>();
        }
        this.papers.add(course);
        notifyItemInserted(papers.size() - 1);
    }
}