package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.humblecoder.pyp.model.Paper;
import com.humblecoder.pyp.model.Question;
import com.humblecoder.pyp.widget.PYPDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * show a list of question of a particular paper. For
 * each question, number of available answers will be
 * shown too.
 *
 * A paper id must be given by previous activity so
 * that a list of question of that semester for a
 * course can be shown. The course id should be
 * put in the intent that start the activity. The key
 * for the extra intent is "objectId".
 *
 * A list of answer will be shown if a question is chosen.
 *
 * @see com.humblecoder.pyp.AnswerListActivity
 * @see android.content.Intent
 *
 * @author HumbleCoder
 */
public class QuestionListActivity extends Activity {

    @InjectView(R.id.activity_question_list_view)
    RecyclerView recyclerView;

    private QuestionListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    PYPDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question_list);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No paper id was given by previous activity");
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
        mAdapter = new QuestionListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        int API_LEVEL = Build.VERSION.SDK_INT;
        if (API_LEVEL >= 21) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Timber.d("adding animation");
        }

        ParseQuery<Question> query = ParseQuery.getQuery(Question.class);
        ParseObject object = ParseObject.create("Paper");
        object.setObjectId(objectId);

        query.whereEqualTo("paper",object);
        query.addAscendingOrder("questionNo");

        dialog = PYPDialog.showProgress(QuestionListActivity.this, "Loading");
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> questions, ParseException e) {
                dialog.dismiss();
                if(e==null) {
                    mAdapter.setQuestions(questions);
                    Timber.d("Get questions successfully: " + questions.size());
                }else{
                    Timber.e("Cannot retrieve questions objects");
                }
            }
        });
    }
}
