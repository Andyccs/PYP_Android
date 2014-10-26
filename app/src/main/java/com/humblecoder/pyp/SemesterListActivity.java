package com.humblecoder.pyp;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.humblecoder.pyp.adapter.SemesterListAdapter;
import com.humblecoder.pyp.model.Paper;
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
 * Show a list of semester.
 *
 * <p>A course id must be given by
 * previous activity so that a list of semester of that
 * particular course can be shown. The course id should be
 * put in the intent that start the activity. The key
 * for the extra intent is "objectId".</p>
 *
 * A list of question will be shown if a semester is choosen
 *
 * @see com.humblecoder.pyp.QuestionListActivity
 * @see android.content.Intent
 *
 * @author HumbleCoder
 */
public class SemesterListActivity extends PYPActivity {

    @InjectView(R.id.activity_semester_list_view)
    RecyclerView recyclerView;

    private SemesterListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    PYPDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_semester_list);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No course id was given by previous activity");
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
        mAdapter = new SemesterListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        int API_LEVEL = Build.VERSION.SDK_INT;
        if (API_LEVEL >= 21) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Timber.d("adding animation");
        }

        ParseQuery<Paper> query = ParseQuery.getQuery(Paper.class);
        ParseObject object = ParseObject.create("Course");
        object.setObjectId(objectId);

        query.whereEqualTo("course",object);

        dialog = PYPDialog.showProgress(SemesterListActivity.this, "Loading");
        query.findInBackground(new FindCallback<Paper>() {
            @Override
            public void done(List<Paper> papers, ParseException e) {
                dialog.dismiss();
                if(e==null) {
                    mAdapter.setPapers(papers);
                    Timber.d("Get papers successfully: "+papers.size());
                }else{
                    Timber.e("Cannot retrieve papers objects");
                }
            }
        });


    }

}
