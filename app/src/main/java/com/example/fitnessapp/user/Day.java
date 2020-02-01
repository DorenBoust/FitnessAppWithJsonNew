package com.example.fitnessapp.user;

import java.util.List;

public class Day {

    List<Exercise> exercises;

    public Day(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Day{" +
                "exercises=" + exercises +
                '}';
    }
}
