package com.java.demo.springboot.models;

public class CoursStatsDTO {
    private Long courseId;
    private String courseTitle;
    private Long totalStudents;
    private Long totalPaidStudents;


    
    public CoursStatsDTO() {
    }

    
    public CoursStatsDTO(Long courseId, String courseTitle, Long totalStudents, Long totalPaidStudents) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.totalStudents = totalStudents;
        this.totalPaidStudents = totalPaidStudents;
    }


    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    public Long getTotalStudents() {
        return totalStudents;
    }
    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }
    public Long getTotalPaidStudents() {
        return totalPaidStudents;
    }
    public void setTotalPaidStudents(Long totalPaidStudents) {
        this.totalPaidStudents = totalPaidStudents;
    }

    
}

