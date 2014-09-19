package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.R.layout.*;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SubmitAnswerActivity extends Activity {

    @InjectView(R.id.course_selection)
    Spinner courseSelection;

    @InjectView(R.id.year_selection)
    Spinner yearSelection;

    @InjectView(R.id.semester_selection)
    RadioGroup semesterSelection;

    @InjectView(R.id.question_number)
    EditText questionNumber;

    @InjectView(R.id.answer_image)
    ImageView answer;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_answer);

        ButterKnife.inject(this);
        context = this;

        Thread initialization = new Thread(new Runnable() {
            @Override
            public void run() {
                //get course here
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
                adapter.addAll("CZ3002 Advanced Computer Eng","CZ3003 Hello World");
                courseSelection.setAdapter(adapter);
            }
        });
        initialization.run();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submit_answer, menu);
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
