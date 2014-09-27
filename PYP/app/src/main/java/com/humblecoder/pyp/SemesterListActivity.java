package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.humblecoder.pyp.model.Course;
import com.humblecoder.pyp.model.Paper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class SemesterListActivity extends Activity {

    @InjectView(R.id.activity_semester_list_view)
    RecyclerView recyclerView;

    private SemesterListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_semester_list);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No course name was given by previous activity");
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
        query.findInBackground(new FindCallback<Paper>() {
            @Override
            public void done(List<Paper> papers, ParseException e) {
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
