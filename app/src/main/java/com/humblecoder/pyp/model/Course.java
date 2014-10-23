package com.humblecoder.pyp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("Course")
public class Course extends ParseObject{

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

    public String getCourseDescription(){
        return getString("courseDescription");
    }

    public void setCourseDescription(String courseDescription){
        put("courseDescription", courseDescription);
        saveInBackground();
    }

    public String getCourseTitle(){
        return getString("courseTitle");
    }

    public void setCourseTitle(String courseTitle){
        put("courseTitle", courseTitle);
        saveInBackground();
    }

}
