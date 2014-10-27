package com.humblecoder.pyp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.robotium.solo.Solo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CreateNewAccountTest extends ActivityInstrumentationTestCase2<CreateNewAccountActivity>{
    private Solo solo;
    private static final String VALID_EMAIL = "abcde12345@e.ntu.edu.sg";
    private static final String INVALID_EMAIL = "abcde12345@hotmail.com";
    private static final String NORMAL_USERNAME = "humblecoder";
    private static final String LONG_USERNAME = "averylongusernametopurposelyfailthestresstest";

    private static final String NORMAL_PASSWORD = "humblecoder";
    private static final String LONG_PASSWORD = "averylongusernametopurposelyfailthestresstest";
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

    public CreateNewAccountTest() {
        super(CreateNewAccountActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
        setActivityInitialTouchMode(false);

        //precondition 1: user has logged out
        ParseUser.logOut();

        //precondition 2: data from previous test has been removed
        deleteTestingParseUserAccount();

        ButterKnife.inject(this,getActivity());

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
    }

    public void testPrecondition() throws Exception {
        solo.waitForActivity(CreateNewAccountActivity.class);
        solo.assertCurrentActivity("Activity is not active", CreateNewAccountActivity.class);
    }

    public void testNormalRegistration() throws InterruptedException, ParseException {
        registerAccount(VALID_EMAIL,NORMAL_USERNAME,NORMAL_PASSWORD);
    }

    public void testRegistrationLongUsername() throws InterruptedException, ParseException  {
        registerAccount(VALID_EMAIL,LONG_USERNAME,NORMAL_PASSWORD);
    }

    public void testRegistrationLongPassword() throws InterruptedException, ParseException  {
        registerAccount(VALID_EMAIL,NORMAL_USERNAME,LONG_PASSWORD);
    }

    public void testRegistrationInvalidEmail() throws InterruptedException{
        try{
            registerAccount(INVALID_EMAIL,NORMAL_USERNAME,NORMAL_PASSWORD);
            fail("Registered with an invalid email address");
        }catch (ParseException e){

        }
    }

    private void registerAccount(String email, String username, String password) throws InterruptedException, ParseException  {
        //enter required text into EditText field
        solo.enterText(emailField,email);
        solo.enterText(usernameField,username);
        solo.enterText(passwordField,password);
        solo.enterText(confirmPasswordField, password);

        //make sure everything is enter correctly
        assertEquals(email, emailField.getText().toString());
        assertEquals(username, usernameField.getText().toString());
        assertEquals(password, passwordField.getText().toString());
        assertEquals(password, confirmPasswordField.getText().toString());

        //start creating account
        solo.clickOnView(createAccountButton);
        Thread.sleep(2000);

        //try login. if login fail, exception will be thrown
        ParseUser.logIn(NORMAL_USERNAME, NORMAL_PASSWORD);

        //login successfully, now, delete the account
        ParseUser.getCurrentUser().delete();

        solo.finishOpenedActivities();
    }

    private void deleteTestingParseUserAccount() throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        List<ParseObject> users = query.find();
        for(ParseObject user : users){
            if(user.get("username").toString().equals(NORMAL_USERNAME) ||
                    user.get("username").toString().equals(LONG_USERNAME) ){
                user.deleteEventually();
            }
        }
    }

    @Override
    public void tearDown() throws Exception {
        ParseUser.logOut();

        deleteTestingParseUserAccount();

        super.tearDown();
    }
}
