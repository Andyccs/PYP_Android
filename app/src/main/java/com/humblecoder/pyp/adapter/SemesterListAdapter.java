package com.humblecoder.pyp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humblecoder.pyp.QuestionListActivity;
import com.humblecoder.pyp.R;
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

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.semester.setText(papers.get(position).getAcademicYear()+ " Semester " + papers.get(position).getSemester());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionListActivity.class);
                intent.putExtra("objectId",papers.get(position).getObjectId());
                context.startActivity(intent);
            }
        });
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