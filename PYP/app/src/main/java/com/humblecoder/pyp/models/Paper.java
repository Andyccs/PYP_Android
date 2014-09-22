package com.humblecoder.pyp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 22-Sep-14.
 */
@ParseClassName("Paper")
public class Paper extends ParseObject {

    public Paper() {}

    public static String getParseClassName() {
        return "Paper";
    }

    public String getAcademicYear() {
        return getString("academicYear");
    }

    public void setAcademicYear(String academicYear) {
        put("academicYear", academicYear);
    }

    public int getSemester() {
        return getInt("semester");
    }

    public void setSemester(int semester) {
        put("semester", semester);
    }

    //Todo: getter and setter for Course
}
