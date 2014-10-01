package com.humblecoder.pyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Course;
import com.humblecoder.pyp.model.Paper;
import com.humblecoder.pyp.model.Question;
import com.humblecoder.pyp.widget.PYPDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

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

    PYPDialog dialog;

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    SubmitAnswerCourseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_answer);

        ButterKnife.inject(this);
        context = this;

        adapter = new SubmitAnswerCourseListAdapter(context);
        courseSelection.setAdapter(adapter);

        ArrayAdapter<String> year = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
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
                try {
                    //submit answer
                    final Course sCourse = (Course) courseSelection.getSelectedItem();
                    final String sYear = (String) yearSelection.getSelectedItem();
                    final int semester = semesterSelection.getCheckedRadioButtonId() == R.id.semester1 ? 1 : 2;
                    final String sQno = questionNumber.getEditableText().toString();

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) answer.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
//                    final Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    final ParseFile pFile = new ParseFile("data.jpg", imageInByte);

                    Timber.d("Course: "+sCourse.getCourseTitle());
                    Timber.d("Year: "+sYear);
                    Timber.d("semester: "+semester);
                    Timber.d("sQno: "+sQno);

                    dialog = PYPDialog.showProgress(SubmitAnswerActivity.this, "Loading");
                    ParseQuery query = ParseQuery.getQuery(Paper.class);
                    query.whereEqualTo("academicYear",sYear);
                    query.whereEqualTo("course",sCourse);
                    query.whereEqualTo("semester",semester);
                    query.findInBackground(new FindCallback<Paper>(){
                        @Override
                        public void done(List<Paper> papers, ParseException e) {
                            Paper paper;
                            if(e==null && papers.size()>0){
                                paper = papers.get(0);
                                final ParseQuery query = ParseQuery.getQuery(Question.class);
                                query.whereEqualTo("paper",paper);
                                query.whereEqualTo("questionNo",sQno);
                                query.findInBackground(new FindCallback<Question>(){
                                    @Override
                                    public void done(List<Question> questions, ParseException e) {
                                        Question question;
                                        if(e==null && questions.size()>0){
                                            question = questions.get(0);
                                            Answer a = ParseObject.create(Answer.class);
                                            a.put("question",question);
                                            a.put("answer",pFile);
                                            a.put("user", ParseUser.getCurrentUser());
                                            a.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if(e==null) {
                                                        Timber.d("done submitting answer");
                                                        dialog.dismiss();
                                                        finish();
                                                    }else{
                                                        Timber.e(e.getMessage());
                                                    }
                                                }
                                            });
                                        }else{
                                            dialog.dismiss();
                                            Timber.d("cannot find question");
                                        }
                                    }
                                });
                            }
                            else{
                                Timber.d("cannot find paper");
                                dialog.dismiss();
                            }
                        }
                    });
                }catch (Exception e){
                    Timber.e("submit answer fail");
                    dialog.dismiss();
                }
            }
        });

        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        dialog = PYPDialog.showProgress(SubmitAnswerActivity.this, "Loading");
        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                dialog.dismiss();
                if(e==null) {
                    adapter.setCourses(courses);
                    Timber.d("Get courses successfully: " + courses.size());
                }else{
                    Timber.e("Cannot retrieve Courses objects");
                }
            }
        });

        year.addAll("2010-2011", "2011-2012", "2012-2013", "2013-2014", "2014-2015");

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
