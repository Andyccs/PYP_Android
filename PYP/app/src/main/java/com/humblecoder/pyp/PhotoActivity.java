package com.humblecoder.pyp;

import com.humblecoder.pyp.model.Answer;
import com.humblecoder.pyp.model.Flag;
import com.humblecoder.pyp.util.SystemUiHider;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


/**
 * A full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * This activity show an answer in picture format. A answer
 * id must be given by previous activity so that a
 * answer of that particular course can be shown.
 * The answer id should be put in the intent that
 * start the activity. The key for the extra intent is "objectId".
 *
 * User can navigate to {@code CommentListActivity} from here.
 *
 * @see com.humblecoder.pyp.CommentListActivity
 * @see android.content.Intent
 * @see SystemUiHider
 *
 * @author HumbleCoder
 */
public class PhotoActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private Answer _answer = null;

    @InjectView(R.id.fullscreen_content)
    ImageView answer;

    @InjectView(R.id.activity_photo_flag_button)
    Button btnFlag;

    @OnClick(R.id.activity_photo_comment_button)
    public void btnComment_onClick() {

        Intent intent = new Intent(this, CommentListActivity.class);
        intent.putExtra("objectId",_answer.getObjectId());
        startActivity(intent);

    }

    @OnClick(R.id.activity_photo_flag_button)
    public void btnFlag_onClick() {

        try {

            ParseQuery<Flag> flagParseQuery = new ParseQuery<Flag>(Flag.getParseClassName());
            flagParseQuery.whereEqualTo("answer", _answer)
                    .whereEqualTo("user", ParseUser.getCurrentUser());
            List<Flag> flagList = flagParseQuery.find();


            if(flagList.isEmpty()) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                final EditText txtMessage = new EditText(this);

                alertDialogBuilder.setTitle("Report")
                        .setMessage("What is the issue?")
                        .setView(txtMessage)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Flag flag = new Flag();
                                flag.setUser(ParseUser.getCurrentUser());
                                flag.setAnswer(_answer);
                                flag.setMessage(txtMessage.getText().toString());

                                try {
                                    flag.save();
                                } catch (ParseException e) {
                                    Toast.makeText(PhotoActivity.this, "Report cannot be sent now. Please try again later.", Toast.LENGTH_LONG).show();
                                    Timber.e("Report cannot be sent now. Please try again later.");
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(this, "You have already reported.", Toast.LENGTH_LONG).show();
            }

//            ParseQuery<Answer> query = new ParseQuery<Answer>(Answer.getParseClassName());
//            Answer answer = query.get(answerId);

        } catch (ParseException e) {
            Toast.makeText(this, "Connection Problem", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo);
        setupActionBar();

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        btnFlag.setOnTouchListener(mDelayHideTouchListener);

        //start coding from here
        ButterKnife.inject(this);

        String objectId = getIntent().getStringExtra("objectId");
        if(objectId == null){
            Timber.e("No answer id was given by previous activity");
            finish();
        }

        ParseQuery<Answer> answerParseQuery = new ParseQuery<Answer>(Answer.getParseClassName());
        try {
            _answer = answerParseQuery.get(objectId);
        } catch(ParseException e) {
            Timber.e("Answer object cannot be retrieved");
            e.printStackTrace();
        }

        String url = getIntent().getStringExtra("photo_url");
        if(url==null){
            Timber.e("no url is provided by previous activity");
            finish();
        }
        Picasso.with(getApplicationContext()).load(url).into(answer);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
