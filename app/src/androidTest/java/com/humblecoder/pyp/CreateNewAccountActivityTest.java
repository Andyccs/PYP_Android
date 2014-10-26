package com.humblecoder.pyp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.humblecoder.pyp.LoginActivity;
import com.jayway.android.robotium.solo.Solo;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class CreateNewAccountActivityTest extends ActivityInstrumentationTestCase2<CreateNewAccountActivity>{
    private Solo solo;
    private static final String VALID_EMAIL = "andy0017@e.ntu.edu.sg";
    private static final String NORMAL_USERNAME = "humblecoder";
    private static final String LONG_USERNAME = "averylongusernametopurposelyfailthestresstest";

    private static final String NORMAL_PASSWORD = "humblecoder";
    @InjectView(R.id.email)
    EditText emailField;

    @InjectView(R.id.username)
    EditText usernameField;

    @InjectView(R.id.password)
    EditText passwordField;

    @InjectView(R.id.confirm_password)
    EditText confirmPasswordField;

    @InjectView(R.id.create_account_button)
    Button createAccountButton;

    public CreateNewAccountActivityTest() {
        super(CreateNewAccountActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());

        //precondition 1: user has logged out
        ParseUser.logOut();

        //precondition 2: data from previous test has been removed
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        List<ParseObject> users = query.find();
        for(ParseObject user : users){
            if(user.get("username").toString().equals(NORMAL_USERNAME) ||
                    user.get("username").toString().equals(LONG_USERNAME) ){
                user.deleteEventually();
            }
        }
    }

    public void testPrecondition() throws Exception {
        solo.waitForActivity(CreateNewAccountActivity.class);
        solo.assertCurrentActivity("Activity is not active", CreateNewAccountActivity.class);
    }

    public void testNormalRegistration(){
        //clear all the field first
        solo.clearEditText(emailField);
        solo.clearEditText(usernameField);
        solo.clearEditText(passwordField);
        solo.clearEditText(confirmPasswordField);

        solo.enterText(emailField,VALID_EMAIL);
        solo.enterText(usernameField,NORMAL_USERNAME);
        solo.enterText(passwordField,NORMAL_PASSWORD);
        solo.enterText(confirmPasswordField, NORMAL_PASSWORD);
    }

    public void testRegistrationLongUsername(){
    }

    @Override
    public void tearDown() throws Exception {
        ParseUser.logOut();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        List<ParseObject> users = query.find();
        for(ParseObject user : users){
            if(user.get("username").toString().equals(NORMAL_USERNAME) ||
                    user.get("username").toString().equals(LONG_USERNAME) ){
                user.deleteEventually();
            }
        }

        super.tearDown();
    }
}
