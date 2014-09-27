package com.humblecoder.pyp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.humblecoder.pyp.model.Course;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

@TargetApi(21)
public class CourseListActivity extends Activity {

    @InjectView(R.id.activity_course_list_view)
    RecyclerView recyclerView;

    private CourseListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_list);

        ButterKnife.inject(this);

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        int API_LEVEL = Build.VERSION.SDK_INT;
        if (API_LEVEL >= 21) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Timber.d("adding animation");
        }

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Course");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    int i = 0;
//                    for(ParseObject o: objects){
//                        Course course = new Course(
//                                o.getObjectId(),
//                                o.get("courseCode").toString(),
//                                o.get("courseTitle").toString(),
//                                o.get("courseDescription").toString());
//                        mAdapter.addCourse(course);
//                    }
//                } else {
//                    //objectRetrievalFailed();
//                    Timber.e(e.getMessage());
//                }
//            }
//        });
        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                if(e==null) {
                    mAdapter.setCourses(courses);
                    Timber.d("Get courses successfully: "+courses.size());
                }else{
                    Timber.e("Cannot retrieve Courses objects");
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.course_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_logout){
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
