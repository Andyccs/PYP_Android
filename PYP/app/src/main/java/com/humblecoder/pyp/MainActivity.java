package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.humblecoder.pyp.models.Answer;
import com.humblecoder.pyp.models.Question;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    List<Answer> answers;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

        ParseQuery<ParseObject> query = ParseQuery.getQuery(Answer.getParseClassName());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    answers = new ArrayList<Answer>();
                    System.out.println("Here"+parseObjects);
                    for(ParseObject o : parseObjects){
                        System.out.println("Here"+o);
                        answers.add((Answer) o);
                    }
                } else {
                    Log.e("Error", "Cannot retrieve Question objects");
                }
            }
        });

        for(int i = 0; i < answers.size(); i++) {
            Toast.makeText(this,"Content: " + answers.get(i).getContent() + ", Content Type: " + answers.get(i).getContentType()
                    + ", Question: " + answers.get(i).getQuestion() + ", Ranking: " + answers.get(i).getRanking(),Toast.LENGTH_LONG).show();
        }
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
