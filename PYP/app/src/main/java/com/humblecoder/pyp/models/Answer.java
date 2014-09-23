package com.humblecoder.pyp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by WeeLiang on 19/9/2014.
 */
@ParseClassName ("Answer")
public class Answer extends ParseObject{

    private String content;
    private int contentType;
    private Question question;
    private int ranking;
    //private User user;

    public Answer(){
    }

    public static String getParseClassName(){
        return "Answer";
    }
    public String getContent() {
        return getString("content");
    }

    public int getContentType() {
        return getInt("contentType");
    }

    public ParseObject getQuestion() {
        //return getParseObject(Question.getParseClassName());
        return getParseObject("question");
    }

    public int getRanking() {
        return getInt("ranking");
    }

//    public User getUser() {
//        return (User)get("user");
//    }


}
