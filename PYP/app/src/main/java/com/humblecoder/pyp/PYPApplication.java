package com.humblecoder.pyp;

import android.app.Application;

import com.humblecoder.pyp.model.*;
import com.parse.Parse;
import com.parse.ParseObject;

import timber.log.Timber;

/**
 * Created by Andy on 9/22/2014.
 */
public class PYPApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        ParseObject.registerSubclass(Answer.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(com.humblecoder.pyp.model.Course.class);
        ParseObject.registerSubclass(Flag.class);
        ParseObject.registerSubclass(Paper.class);
        ParseObject.registerSubclass(Question.class);

        Parse.initialize(this, "cztzxFVJLJ3PCoSGJeyWU9PX0S8nsNlXtIIwIV98", "VZnqAvCGLZiBaDcrSPFLAY8jgQhP5dwUJdAfRbAx");

    }
}
