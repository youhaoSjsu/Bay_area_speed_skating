package com.example.student_portal.module;

public class ClassDescription {

    public String name;

    public int id;
    public String picLink;
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClassDescription(int id, String name, String description, String picLink) {
        this.id =id;
        this.name = name;
        this.picLink = picLink;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
