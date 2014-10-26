package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.humblecoder.pyp.adapter.AnswerListAdapter;
import com.humblecoder.pyp.model.Answer;
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
 * <p>Show a list of answer.</p>
 *
 * <p>A question id must be given by previous activity so that a
 * list of answer that particular question can be shown. The question id
 * should be put in the intent that start the activity. The key
 * for the extra intent is "objectId".</p>
 *
 * @author HumbleCoder
 */
public class AnswerListActivity extends Activity {

    @InjectView(R.id.activity_answer_list_view)
    RecyclerView recyclerView;

    private AnswerListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    PYPDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_answer_list);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No question id was given by previous activity");
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
        mAdapter = new AnswerListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        int API_LEVEL = Build.VERSION.SDK_INT;
        if (API_LEVEL >= 21) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Timber.d("adding animation");
        }

        ParseQuery<Answer> query = ParseQuery.getQuery(Answer.class);
        ParseObject object = ParseObject.create("Question");
        object.setObjectId(objectId);

        query.whereEqualTo("question",object);
        query.addAscendingOrder("ranking");

        dialog = PYPDialog.showProgress(AnswerListActivity.this, "Loading");
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
