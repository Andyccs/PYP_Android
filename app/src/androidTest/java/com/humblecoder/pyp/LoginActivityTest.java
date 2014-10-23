package com.humblecoder.pyp;

import android.test.ActivityInstrumentationTestCase2;

import com.humblecoder.pyp.LoginActivity;
import com.jayway.android.robotium.solo.Solo;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{
    private Solo solo;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testPrecondition() throws Exception {
        assertNotNull("Activity is null",solo.getCurrentActivity());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
