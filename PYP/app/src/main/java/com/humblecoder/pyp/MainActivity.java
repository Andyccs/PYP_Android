package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.humblecoder.pyp.models.Answer;
import com.humblecoder.pyp.models.Comment;
import com.humblecoder.pyp.models.Course;
import com.humblecoder.pyp.models.Flag;
import com.humblecoder.pyp.models.Paper;
import com.humblecoder.pyp.models.Question;
import com.humblecoder.pyp.models._User;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ArrayList<Question> questions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = new ArrayList<Question>();

        ParseObject.registerSubclass(Question.class);
        ParseObject.registerSubclass(Paper.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Answer.class);
        ParseObject.registerSubclass(_User.class);
        ParseObject.registerSubclass(Flag.class);

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

        getAllQuestions();
        getAllPapers();
        getAllComments();
        getAllFlags();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void getAllQuestions() {

        ParseQuery<Question> query = ParseQuery.getQuery(Question.class);
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> questions, ParseException e) {
                if (e == null) {
                    for(Question q : questions){

//                        //Update attributes. It Works!!! :)
//                        q.setContentType(1);
//                        //q.setPaper("lgvQJosRx0");

                        //Testing to see if query works
                        Log.d("Test", "Question => Content: " + q.getContent() + ", Content Type: " + q.getContentType()
                                + ", Question No: " + q.getQuestionNo() + ", Paper: " + q.getPaper().getObjectId());
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Question objects");
                }
            }
        });
    }

    public void getAllPapers() {

        ParseQuery<Paper> query = ParseQuery.getQuery(Paper.class);
        query.findInBackground(new FindCallback<Paper>() {
            @Override
            public void done(List<Paper> papers, ParseException e) {
                if (e == null) {
                    for(Paper p : papers){

                        //Testing to see if query works
                        Log.d("Test", "Paper => Course: " + p.getCourse().getObjectId() + ", Academic Year: " + p.getAcademicYear()
                                + ", Semester: " + p.getSemester());
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Paper objects");
                }
            }
        });
    }

    public void getAllComments() {

        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e == null) {
                    for(Comment c : comments){

                        //Testing to see if query works
                        Log.d("Test", "Comment => Answer: " + c.getAnswer().getObjectId() + ", Content: " + c.getContent()
                        + ", Content Type: " + c.getContentType() + ", User: " + c.getUser().getObjectId());
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Comment objects");
                }
            }
        });
    }

    public void getAllFlags() {

        ParseQuery<Flag> query = ParseQuery.getQuery(Flag.class);
        query.findInBackground(new FindCallback<Flag>() {
            @Override
            public void done(List<Flag> flags, ParseException e) {
                if (e == null) {
                    for(Flag f : flags){

                        //Testing to see if query works
                        Log.d("Test", "Flag => Answer: " + f.getAnswer().getObjectId() + ", Message: " + f.getMessage()
                                + ", User: " + f.getUser().getObjectId());
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Flag objects");
                }
            }
        });
    }
}
