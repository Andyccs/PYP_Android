package com.humblecoder.pyp;

/**
 * Created by Andy on 9/23/2014.
 */
public class Course {
    private String objectId;
    private String courseCode;
    private String courseName;
    private String courseDescription;

    public Course(String objectId, String courseCode, String courseName, String courseDescription) {
        this.objectId = objectId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
