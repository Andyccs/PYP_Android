package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.humblecoder.pyp.model.Course;
import com.humblecoder.pyp.model.Paper;
import com.humblecoder.pyp.widget.PYPDialog;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.InjectView;
import timber.log.Timber;

/**
 * Allow user to search for an answer.
 *
 * @author HumbleCoder
 */
public class SearchActivity extends Activity {

    private ArrayList<String> course_code_list = null;
    private ArrayList<String> academic_yr_list = null;
    private ArrayList<Integer> semester_list = null;

    private Spinner course_code_spinner;
    private Spinner academic_yr_spinner;
    private Spinner semester_spinner;

    private PYPDialog dialog;
    private Button submit_search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        course_code_list = new ArrayList<String>();
        academic_yr_list = new ArrayList<String>();
        semester_list = new ArrayList<Integer>();

        course_code_spinner = (Spinner) findViewById(R.id.course_code_spinner);
        academic_yr_spinner = (Spinner) findViewById(R.id.academic_yr_spinner);
        semester_spinner = (Spinner) findViewById(R.id.semester_spinner);

        submit_search_button = (Button) findViewById(R.id.submit_search_button);

        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Paper.class);

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

//        populateCourseCodeSpinner(course_code_list, course_code_spinner);
//        populateAcademicYearSpinner(academic_yr_list, academic_yr_spinner);
//        populateSemesterSpinner(semester_list, semester_spinner);

        dialog = PYPDialog.showProgress(SearchActivity.this, "Loading");
        populateSpinnersInBackground();

        submit_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<Course> innerQuery = ParseQuery.getQuery(Course.class);
                innerQuery.whereEqualTo("courseCode", course_code_spinner.getSelectedItem());

                ParseQuery<Paper> query = ParseQuery.getQuery(Paper.class);
                query.whereMatchesQuery("course", innerQuery);
                query.whereEqualTo("academicYear", academic_yr_spinner.getSelectedItem());
                query.whereEqualTo("semester", semester_spinner.getSelectedItem());

                query.findInBackground(new FindCallback<Paper>() {
                    @Override
                    public void done(List<Paper> papers, ParseException e) {
                        if(e == null) {
                            if(papers.size() == 0) {
                                Toast.makeText(SearchActivity.this, "There is no such paper.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            Timber.d("Paper ObjectId: " + papers.get(0).getObjectId());
                            Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
                            intent.putExtra("objectId", papers.get(0).getObjectId());
                            startActivity(intent);
                        }
                        else {
                            Timber.e("Cannot retrieve Paper objects");
                        }
                    }
                });
            }
        });
    }

    private void populateCourseCodeSpinner(final ArrayList<String> course_code_list, final Spinner course_code_spinner) {

        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                if(e == null) {
                    for(Course course : courses) {
                        course_code_list.add(course.getCourseCode());
                    }
                    Collections.sort(course_code_list);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, course_code_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    course_code_spinner.setAdapter(adapter);
                }
                else {
                    Timber.e("Cannot retrieve Course objects");
                }
            }
        });
    }

    private void populateAcademicYearSpinner(final ArrayList<String> academic_yr_list, final Spinner academic_yr_spinner) {

        ParseQuery<Paper> query = ParseQuery.getQuery(Paper.class);
        query.findInBackground(new FindCallback<Paper>() {
            @Override
            public void done(List<Paper> papers, ParseException e) {
                if(e == null) {
                    for(Paper paper : papers) {
                        academic_yr_list.add(paper.getAcademicYear());
                    }
                    HashSet<String> hs = new HashSet<String>(academic_yr_list);
                    academic_yr_list.clear();
                    academic_yr_list.addAll(hs);
                    Collections.sort(academic_yr_list);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, academic_yr_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    academic_yr_spinner.setAdapter(adapter);
                }
                else {
                    Timber.e("Cannot retrieve Paper objects");
                }
            }
        });
    }

    private void populateSemesterSpinner(final ArrayList<Integer> semester_list, final Spinner semester_spinner) {

        ParseQuery<Paper> query = ParseQuery.getQuery(Paper.class);
        query.findInBackground(new FindCallback<Paper>() {
            @Override
            public void done(List<Paper> papers, ParseException e) {
                dialog.dismiss();
                if(e == null) {
                    for(Paper paper : papers) {
                        semester_list.add(paper.getSemester());
                    }
                    HashSet<Integer> hs = new HashSet<Integer>(semester_list);
                    semester_list.clear();
                    semester_list.addAll(hs);
                    Collections.sort(semester_list);

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, semester_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    semester_spinner.setAdapter(adapter);
                }
                else {
                    Timber.e("Cannot retrieve Paper objects");
                }
            }
        });
    }

    private Runnable populateSpinners = new Runnable() {
        @Override
        public void run() {
            populateCourseCodeSpinner(course_code_list, course_code_spinner);
            populateAcademicYearSpinner(academic_yr_list, academic_yr_spinner);
            populateSemesterSpinner(semester_list, semester_spinner);
        }
    };

    private void populateSpinnersInBackground() {
        this.runOnUiThread(populateSpinners);
    }
}
