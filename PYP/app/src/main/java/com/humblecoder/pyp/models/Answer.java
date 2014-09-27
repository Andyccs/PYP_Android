package com.humblecoder.pyp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("Answer")
public class Answer extends ParseObject{

    public Answer() {}

    public static String getParseClassName() {
        return "Answer";
    }
}
