package com.example.fitnessapp.user;

import java.util.List;

public class User {

    String name;
    String bDay;
    String height;
    String job;
    String phoneNumber;
    String email;
    String goal;
    String limitation;
    List<Day> days;

    public User(String name, String bDay, String height, String job, String phoneNumber, String email, String goal, String limitation, List<Day> days) {
        this.name = name;
        this.bDay = bDay;
        this.height = height;
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.goal = goal;
        this.limitation = limitation;
        this.days = days;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getbDay() {
        return bDay;
    }
    public void setbDay(String bDay) {
        this.bDay = bDay;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGoal() {
        return goal;
    }
    public void setGoal(String goal) {
        this.goal = goal;
    }
    public String getLimitation() {
        return limitation;
    }
    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }
    public List<Day> getDays() {
        return days;
    }
    public void setDays(List<Day> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", bDay='" + bDay + '\'' +
                ", height='" + height + '\'' +
                ", job='" + job + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", goal='" + goal + '\'' +
                ", limitation='" + limitation + '\'' +
                ", days=" + days +
                '}';
    }
}

