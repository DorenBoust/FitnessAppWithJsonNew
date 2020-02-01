package com.example.fitnessapp.user;

public class Exercise {
    String exName;
    String sets;
    String repitition;
    String rest;
    String notes;
    String image;
    String videoLink;

    public Exercise(String exName, String sets, String repitition, String rest, String notes, String image, String videoLink) {
        this.exName = exName;
        this.sets = sets;
        this.repitition = repitition;
        this.rest = rest;
        this.notes = notes;
        this.image = image;
        this.videoLink = videoLink;
    }

    public String getExName() {
        return exName;
    }
    public void setExName(String exName) {
        this.exName = exName;
    }
    public String getSets() {
        return sets;
    }
    public void setSets(String sets) {
        this.sets = sets;
    }
    public String getRepitition() {
        return repitition;
    }
    public void setRepitition(String repitition) {
        this.repitition = repitition;
    }
    public String getRest() {
        return rest;
    }
    public void setRest(String rest) {
        this.rest = rest;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getVideoLink() {
        return videoLink;
    }
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exName='" + exName + '\'' +
                ", sets='" + sets + '\'' +
                ", repitition='" + repitition + '\'' +
                ", rest='" + rest + '\'' +
                ", notes='" + notes + '\'' +
                ", image='" + image + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }
}
