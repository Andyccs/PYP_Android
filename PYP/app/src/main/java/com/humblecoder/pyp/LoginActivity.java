package com.humblecoder.pyp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


public class LoginActivity extends Activity {

    @InjectView(R.id.username)
    EditText usernameField;

    @InjectView(R.id.password)
    EditText passwordField;

    @InjectView(R.id.login_button)
    Button login;

    @InjectView(R.id.create_account_button)
    Button createAccount;

    @OnClick(R.id.login_button)
    public void login(){
        Timber.d("Loging in");
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if(username.isEmpty()||password.isEmpty()){

        }else{
            //try login
            Timber.d("username: "+username+", password: "+password);
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        // Hooray! The user is logged in.
                        Timber.d("Loged in");
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.zoom_out_from_top_to_center,0);

        ButterKnife.inject(this);
    }
}
