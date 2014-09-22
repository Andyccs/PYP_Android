package com.humblecoder.pyp;

import android.app.Application;

import com.parse.Parse;

import timber.log.Timber;

/**
 * Created by Andy on 9/22/2014.
 */
public class PYPApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

    }
}
