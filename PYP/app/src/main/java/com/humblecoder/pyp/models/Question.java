package com.humblecoder.pyp.models;

import com.parse.ParseObject;

/**
 * Created by User on 19-Sep-14.
 */
public class Question extends ParseObject {

    public static String getParseClassName() {
        return "Question";
    }

    public String getQuestionID() {
        return getObjectId();
    }

    public String getContent() {
        return getString("content");
    }

    public int getContentType() {
        return getInt("contentType");
    }

    public String getQuestionNo() {
        return getString("questionNo");
    }

    //Todo: create method to get "paper" form Question Table
}
