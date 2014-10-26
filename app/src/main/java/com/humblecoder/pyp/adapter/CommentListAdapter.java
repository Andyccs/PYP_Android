package com.humblecoder.pyp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.humblecoder.pyp.R;
import com.humblecoder.pyp.model.Comment;
import com.ocpsoft.pretty.time.PrettyTime;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Soe Lynn on 13/10/2014.
 */
@TargetApi(21)
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout view;

        @InjectView(R.id.comment_list_item_username)
        TextView txtUsername;

        @InjectView(R.id.comment_list_item_time)
        TextView txtTime;

        @InjectView(R.id.comment_list_item_comment)
        TextView txtComment;

        public ViewHolder(FrameLayout v) {
            super(v);
            view = v;
            ButterKnife.inject(this, v);
        }
    }

    private Context context;

    private List<Comment> comments;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentListAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Comment comment = comments.get(position);
        ParseUser user = comment.getUser();

        holder.txtUsername.setText(user.getUsername());
        holder.txtComment.setText(comment.getContent());
//        holder.txtTime.setText(comment.getCreatedAt().toString());

        PrettyTime p = new PrettyTime();

        holder.txtTime.setText(p.format(comment.getCreatedAt()));
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return mDataset.length;
        return comments !=null? comments.size():0;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public void addComments(Comment comment){
        if(this.comments == null){
            comments = new ArrayList<Comment>();
        }
        this.comments.add(comment);
        notifyItemInserted(comments.size() - 1);
    }
}