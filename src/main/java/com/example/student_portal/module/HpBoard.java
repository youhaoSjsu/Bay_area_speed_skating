package com.example.student_portal.module;

public class HpBoard {

    private int id;
    private int type;
    private String name;

    private int number;

    private String description;

    private String imageLinks;

    public HpBoard(int id, String name, int type, int number, String description, String imageLinks) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.type = type;
        this.description = description;
        this.imageLinks = imageLinks;
    }

    public HpBoard() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }
}
