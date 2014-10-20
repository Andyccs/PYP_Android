package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Comment;
import com.humblecoder.pyp.widget.PYPDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Show a list of comment for a particular answer.
 * User can also make comment for an answer.
 *
 * <p>A answer id must be given by previous activity so that a
 * list of comment that particular answer can be shown. The answer id
 * should be put in the intent that start the activity. The key
 * for the extra intent is "objectId".</p>
 *
 * @see android.content.Intent
 *
 * @author HumbleCoder
 */
public class CommentListActivity extends Activity {

    @InjectView(R.id.activity_comment_list_view)
    RecyclerView recyclerView;

    @InjectView(R.id.activity_comment_list_comment_textview)
    EditText txtComment;

    @OnClick(R.id.activity_comment_list_comment_button)
    public void btnComment_onClick() {

        ParseObject comment = ParseObject.create(Comment.getParseClassName());
        comment.put("answer", answer);
        comment.put("user", ParseUser.getCurrentUser());
        comment.put("content", txtComment.getText().toString());
        comment.saveEventually();

        txtComment.setText("");
        commentParseQuery.findInBackground(dataCallback);
    }

    private CommentListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private Answer answer = null;

    private ParseQuery<Comment> commentParseQuery;

    PYPDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No answer id was given by previous activity");
            finish();
        }

        ButterKnife.inject(this);

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CommentListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        int API_LEVEL = Build.VERSION.SDK_INT;
        if (API_LEVEL >= 21) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Timber.d("adding animation");
        }

        commentParseQuery = new ParseQuery<Comment>(Comment.getParseClassName());
        answer = new Answer();
        answer.setObjectId(objectId);

        commentParseQuery.whereEqualTo("answer", answer);
        commentParseQuery.orderByAscending("createdAt");
        commentParseQuery.include("_User");

        dialog = PYPDialog.showProgress(CommentListActivity.this, "Loading");
        commentParseQuery.findInBackground(dataCallback);

    }

    private FindCallback<Comment> dataCallback = new FindCallback<Comment>() {
        @Override
        public void done(List<Comment> comments, ParseException e) {
            dialog.dismiss();

            if(e==null) {
                mAdapter.setComments(comments);
                recyclerView.scrollToPosition(comments.size()-1);
                Timber.d("Get comment successfully: " + comments.size());
            }else{
                Timber.e("Cannot retrieve Comment objects");
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comment_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
