package com.humblecoder.pyp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


public class CreateNewAccountActivity extends PYPActivity {

    @InjectView(R.id.text)
    TextView registerTitle;

    @InjectView(R.id.email)
    EditText emailField;

    @InjectView(R.id.username)
    EditText usernameField;

    @InjectView(R.id.password)
    EditText passwordField;

    @InjectView(R.id.confirm_password)
    EditText confirmPasswordField;

    @OnClick(R.id.create_account_button)
    public void createAccount(){
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()|| !password.equals(confirmPassword)){
            //reject request, show dialog box here
            Timber.w("some fields are empty or password not same");
        }else {

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        Timber.d("Create account complete");
                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // Hooray! The user is logged in.

                                    // TODO go to main page
                                    Intent intent = new Intent(getApplicationContext(),CourseListActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Signup failed.
                                    // Look at the ParseException to see what happened.
                                    Timber.e("Sign up failed");
                                    Timber.e(e.getMessage());
                                }
                            }
                        });

                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Timber.e("Sign up failed");
                        Timber.e(e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        ButterKnife.inject(this);

        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");
        registerTitle.setTypeface(typeFace);


    }
}
