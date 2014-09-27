package com.humblecoder.pyp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Paper;
import com.humblecoder.pyp.model.Question;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andy on 9/22/2014.
 */
@TargetApi(21)
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout view;

        @InjectView(R.id.question)
        TextView question;

        @InjectView(R.id.answer_no)
        TextView answerNo;

        public ViewHolder(FrameLayout v) {
            super(v);
            view = v;
            ButterKnife.inject(this, v);
        }
    }

    private Context context;

    private List<Question> questions;

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionListAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.question.setText("Question "+ questions.get(position).getQuestionNo());

        ParseQuery<Answer> query = ParseQuery.getQuery(Answer.class);
        query.whereEqualTo("question",questions.get(position));
        query.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(final List<Answer> answers, ParseException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        holder.answerNo.setText(""+answers.size());
                    }
                });
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
        return questions !=null? questions.size():0;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    public void addQuestions(Question question){
        if(this.questions == null){
            questions = new ArrayList<Question>();
        }
        this.questions.add(question);
        notifyItemInserted(questions.size() - 1);
    }
}