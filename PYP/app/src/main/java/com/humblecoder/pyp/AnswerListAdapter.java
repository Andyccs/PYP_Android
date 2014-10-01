package com.humblecoder.pyp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Flag;
import com.humblecoder.pyp.model.Question;
import com.humblecoder.pyp.util.DisplayUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andy on 9/22/2014.
 */
@TargetApi(21)
public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ViewHolder> {

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout view;

        @InjectView(R.id.flag_up)
        ImageView flagUp;

        @InjectView(R.id.flag)
        TextView flag;

        @InjectView(R.id.flag_down)
        ImageView flagDown;

        @InjectView(R.id.answer)
        ImageView answer;

        @InjectView(R.id.answer_layout)
        FrameLayout answerLayout;

        public ViewHolder(FrameLayout v) {
            super(v);
            view = v;
            ButterKnife.inject(this, v);
        }
    }

    private Context context;

    private List<Answer> answers;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnswerListAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnswerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        ColorFilter filter = new LightingColorFilter( Color.WHITE, Color.WHITE );
        vh.flagUp.setColorFilter(filter);
        vh.flagDown.setColorFilter(filter);

        vh.flagUp.setVisibility(View.INVISIBLE);
        vh.flagDown.setVisibility(View.INVISIBLE);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DisplayUtils util = new DisplayUtils(Resources.getSystem());

        Picasso.with(context).load(answers.get(position).getContent()).into(holder.answer);

        holder.answerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("photo_url",answers.get(position).getContent());
                context.startActivity(intent);
            }
        });

        ParseQuery<Flag> query = ParseQuery.getQuery(Flag.class);
        query.whereEqualTo("answer", answers.get(position));
        query.findInBackground(new FindCallback<Flag>() {
            @Override
            public void done(final List<Flag> flags, ParseException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int totalFlag = 0;
                        boolean hasVote = false;
                        final ParseUser user = ParseUser.getCurrentUser();
                        for(Flag flag: flags){
                            if(flag.getUser().getObjectId().equals(user.getObjectId())){
                                hasVote = true;
                            }
                            totalFlag += flag.getUpDown();
                        }

                        holder.flag.setText(Integer.toString(totalFlag));

                        if(!hasVote){
                            holder.flagUp.setVisibility(View.VISIBLE);
                            holder.flagDown.setVisibility(View.VISIBLE);

                            holder.flagUp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Flag flag = new Flag();
                                    flag.setMessage("Default Message");
                                    flag.setAnswer(answers.get(position));
                                    flag.setUpDown(1);
                                    flag.setUser(user);
                                    flag.saveInBackground();
//
//                                    ParseObject flag = ParseObject.create(Flag.class);
//                                    flag.put("answer",answers.get(position));
//                                    flag.put("message","Default Message");
//                                    flag.put("upDown",1);
//                                    flag.put("user",user);
//                                    flag.saveInBackground();

                                    holder.flagUp.setVisibility(View.INVISIBLE);
                                    holder.flagDown.setVisibility(View.INVISIBLE);

                                    holder.flag.setText(
                                            Integer.toString(
                                                    Integer.parseInt(holder.flag.getText().toString())+1)
                                    );
                                }
                            });

                            holder.flagDown.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Flag flag = new Flag();
                                    flag.setMessage("Default Message");
                                    flag.setAnswer(answers.get(position));
                                    flag.setUpDown(-1);
                                    flag.setUser(user);
                                    flag.saveInBackground();
                                    holder.flagUp.setVisibility(View.INVISIBLE);
                                    holder.flagDown.setVisibility(View.INVISIBLE);

                                    holder.flag.setText(
                                            Integer.toString(
                                                    Integer.parseInt(holder.flag.getText().toString())-1)
                                    );
                                }
                            });
                        }
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
        return answers !=null? answers.size():0;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        notifyDataSetChanged();
    }

    public void addQuestions(Answer answer){
        if(this.answers == null){
            answers = new ArrayList<Answer>();
        }
        this.answers.add(answer);
        notifyItemInserted(answers.size() - 1);
    }
}