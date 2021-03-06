package com.humblecoder.pyp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("Comment")
public class Comment extends ParseObject {

    public Comment() {}

    public static String getParseClassName() {
        return "Comment";
    }

    public String getCommentId() {
        return getObjectId();
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content", content);
        saveInBackground();
    }

    public int getContentType() {
        return getInt("contentType");
    }

    public void setContentType(int contentType) {
        put("contentType", contentType);
        saveInBackground();
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
}
