package com.humblecoder.pyp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.parse.ParseUser;
import com.robotium.solo.Solo;

import butterknife.ButterKnife;

/**
 * Created by Andy on 10/27/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
        setActivityInitialTouchMode(false);

        //precondition 1: user has logged out
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
    }
}
