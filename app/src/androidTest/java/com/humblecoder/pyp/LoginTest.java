package com.humblecoder.pyp;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.robotium.solo.Solo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * Created by Andy on 10/27/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;
    private static final String USERNAME = "humblecoder";
    private static final String PASSWORD = "humblecoder";
    private static final String EMAIL = "abcde12345@e.ntu.edu.sg";

    @InjectView(R.id.username)
    EditText usernameField;

    @InjectView(R.id.password)
    EditText passwordField;

    @InjectView(R.id.login_button)
    Button login;

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
        setActivityInitialTouchMode(false);

        ParseUser.logOut();

        ButterKnife.inject(this, getActivity());

        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        ParseUser user = new ParseUser();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);

        user.signUp();
    }

    public void testPrecondition() throws Exception {
        solo.waitForActivity(CreateNewAccountActivity.class);
        solo.assertCurrentActivity("Activity is not active", CreateNewAccountActivity.class);
    }

    public void testLoginNormal() throws InterruptedException {
        solo.enterText(usernameField,USERNAME);
        solo.enterText(passwordField,PASSWORD);

        assertEquals(USERNAME,usernameField.getText().toString());
        assertEquals(PASSWORD,passwordField.getText().toString());

        solo.clickOnView(login);
        Thread.sleep(1000);

        assertEquals(USERNAME,ParseUser.getCurrentUser().getUsername());
        ParseUser.logOut();
    }

    @Override
    public void tearDown() throws Exception {
        ParseUser.logOut();
        ParseUser.getCurrentUser().delete();
        super.tearDown();
    }
}
