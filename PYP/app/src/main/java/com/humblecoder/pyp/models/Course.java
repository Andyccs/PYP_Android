package com.humblecoder.pyp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 19-Sep-14.
 */
@ParseClassName("Course")
public class Course extends ParseObject {

    public Course() {}

    public static String getParseClassName() {
        return "Course";
    }

    public String getCourseId() {
        return getObjectId();
    }


    public String getCourseCode() {
        return getString("courseCode");
    }

    public void setCourseCode(String courseCode) {
        put("courseCode", courseCode);
        saveInBackground();
}
