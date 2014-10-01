package com.humblecoder.pyp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("Flag")
public class Flag extends ParseObject{

    public Flag() {
        super("Flag");
    }

    public static String getParseClassName() {
        return "Flag";
    }

    public ParseObject getAnswer() {
        return getParseObject("answer");
    }

    public void setAnswer(Answer answer) {
        put("answer", answer);
    }

    public void updateAnswer(Answer answer) {
        setAnswer(answer);
        saveInBackground();
    }

    public ParseObject getUser() {
        return getParseObject("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public void updateUser(ParseUser user) {
        setUser(user);
        saveInBackground();
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

    public void updateMessage(String message) {
        setMessage(message);
        saveInBackground();
    }

    public int getUpDown() {
        return getInt("upDown");
    }

    public void setUpDown(int upDown) {
        put("upDown", upDown);
    }

    public void updateUpDown(int upDown) {
        setUpDown(upDown);
        saveInBackground();
    }
}
