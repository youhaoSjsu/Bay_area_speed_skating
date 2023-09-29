package com.example.student_portal.module;

import javax.management.loading.PrivateClassLoader;
import java.util.Date;

public class Award {
    private int id;
    private String name;

    private int number;

    private String description;

    private String imageLinks[];


    public Award()
    {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setName(String name) {
        this.name = name;
    }



    public String[] getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String[] imageLinks) {
        this.imageLinks = imageLinks;
    }


}
