package com.humblecoder.pyp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("Flag")
public class Flag extends ParseObject{

    public Flag() {}

    public static String getParseClassName() {
        return "Flag";
    }

    public ParseObject getAnswer() {
        return getParseObject("answer");
    }

    public void setAnswer(String answerId) {
        put("answer", ParseObject.createWithoutData("Answer", answerId));
        saveInBackground();
    }

    public ParseObject getUser() {
        return getParseObject("user");
    }

    public void setUser(String userId) {
        put("user", ParseObject.createWithoutData("User", userId));
        saveInBackground();
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
        saveInBackground();
    }
}
