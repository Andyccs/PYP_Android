package com.humblecoder.pyp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("Course")
public class Course extends ParseObject{

    public Course() {}

    public static String getParseClassName() {
        return "Course";
    }
}
