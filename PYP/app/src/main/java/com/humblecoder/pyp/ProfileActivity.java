package com.humblecoder.pyp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Course;
import com.humblecoder.pyp.widget.PYPDialog;
import com.humblecoder.pyp.widget.RoundDrawable;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class ProfileActivity extends Activity {

    @InjectView(R.id.title)
    TextView username;

    @InjectView(R.id.subtitle)
    TextView email;

    @InjectView(R.id.picture)
    ImageView picture;

    @InjectView(R.id.activity_answer_list_view)
    RecyclerView answerList;

    PYPDialog dialog;

    private AnswerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        ParseUser user = ParseUser.getCurrentUser();

        username.setText(user.getUsername());
        email.setText(user.getEmail());

        try {
            user.fetch();
            byte[] pictureByte = user.getParseFile("picture").getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(pictureByte, 0, pictureByte.length);
            RoundDrawable drawable = new RoundDrawable(bitmap);
            picture.setImageDrawable(drawable);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        answerList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        answerList.setLayoutManager(mLayoutManager);

        mAdapter = new AnswerListAdapter(this);
        answerList.setAdapter(mAdapter);
        ParseQuery<Answer> query = ParseQuery.getQuery(Answer.class);
        query.whereEqualTo("user", user);
        dialog = PYPDialog.showProgress(ProfileActivity.this, "Loading");
        query.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(List<Answer> answer, ParseException e) {
                dialog.dismiss();
                if(e==null) {
                    mAdapter.setAnswers(answer);
                    Timber.d("Get questions successfully: " + answer.size());
                }else{
                    Timber.e("Cannot retrieve questions objects");
                }
            }
        });

    }
}
