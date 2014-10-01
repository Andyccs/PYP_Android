package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

    @InjectView(R.id.submit_button)
    Button submit;

    Context context;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
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

                ArrayAdapter<Integer> year = new ArrayAdapter<Integer>(context, android.R.layout.simple_list_item_1);
                year.addAll(2010,2011,2012,2013,2014,2015);
                yearSelection.setAdapter(year);

                answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //open camera
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //submit answer
                        
                    }
                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            answer.setImageBitmap(imageBitmap);
        }
    }
}
