package com.humblecoder.pyp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.humblecoder.pyp.widget.PYPDialog;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


public class LoginActivity extends PYPActivity {

    @InjectView(R.id.username)
    EditText usernameField;

    @InjectView(R.id.password)
    EditText passwordField;

    @InjectView(R.id.login_button)
    Button login;

    @InjectView(R.id.create_account_button)
    Button createAccount;

    PYPDialog dialog;

    @OnClick(R.id.login_button)
    public void login(){
        Timber.d("Loging in");
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if(username.isEmpty()||password.isEmpty()){

        }else{
            //try login
            dialog = PYPDialog.showProgress(LoginActivity.this, "Logging in");
            ParseUser.logInInBackground(username, password, new LogInCallback() {

                public void done(ParseUser user, ParseException e) {
                    dialog.dismiss();
                    if (user != null) {
                        // Hooray! The user is logged in.
                        Timber.d("Loged in");

                        // TODO go to main page
                        Intent intent = new Intent(getApplicationContext(),CourseListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        Timber.e("Log in failed");
                        Timber.e(e.getMessage());
                    }
                }
            });
        }
    }

    @OnClick(R.id.create_account_button)
    public void createAccount(){
        Timber.d("Creating Account");

        Intent intent = new Intent(this, CreateNewAccountActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.zoom_out_from_top_to_center,R.anim.zoom_out_from_center_to_bottom);

        ButterKnife.inject(this);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            // TODO go to main page
            Intent intent = new Intent(getApplicationContext(),CourseListActivity.class);
            startActivity(intent);
            finish();

        }
    }


}
