package com.java.demo.springboot.models;

public class Statistics {
    private long totalStudents;
    private long totalTrainers;
    private long totalCourses;
    private long totalRegistrations;

    // Constructor
    public Statistics(long totalStudents, long totalTrainers, long totalCourses, long totalRegistrations) {
        this.totalStudents = totalStudents;
        this.totalTrainers = totalTrainers;
        this.totalCourses = totalCourses;
        this.totalRegistrations = totalRegistrations;
    }

    // Getters and setters
    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTrainers() {
        return totalTrainers;
    }

    public void setTotalTrainers(long totalTrainers) {
        this.totalTrainers = totalTrainers;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistrations(long totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }
}

