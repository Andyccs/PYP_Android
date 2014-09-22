package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.humblecoder.pyp.models.Paper;
import com.humblecoder.pyp.models.Question;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    String paperId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(Question.class);
        ParseObject.registerSubclass(Paper.class);

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

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
                        Log.d("Test", "Content: " + q.getContent() + ", Content Type: " + q.getContentType()
                                + ", Question No: " + q.getQuestionNo() + ", Paper: " + q.getPaper().getObjectId());
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Question objects");
                }
            }
        });
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
}
