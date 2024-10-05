package com.java.demo.springboot.models;

import java.util.List;

public class CourseResponse {

    private Cours course;
    private List<User> students;
    public Cours getCourse() {
        return course;
    }
    public void setCourse(Cours course) {
        this.course = course;
    }
    public List<User> getStudents() {
        return students;
    }
    public void setStudents(List<User> students) {
        this.students = students;
    }
    public CourseResponse() {
    }
    public CourseResponse(Cours course, List<User> students) {
        this.course = course;
        this.students = students;
    }


    

}
