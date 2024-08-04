package com.example.quizwiz.models;

public class CategoryModel {
    String id,image,name;
    public CategoryModel(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public CategoryModel() {
    }

    public CategoryModel(String number, int generalKnowledge, String generalKnowledge1) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
