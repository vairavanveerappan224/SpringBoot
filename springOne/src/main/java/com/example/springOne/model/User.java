package com.example.springOne.model;

public class User {

    private int id;
    private String name;
    private String course;

    public User() {
        // empty constructor required for JSON -> object conversion
    }

    public User(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String setCourse(String course) {
        this.course = course;
        return course;
    }
}
