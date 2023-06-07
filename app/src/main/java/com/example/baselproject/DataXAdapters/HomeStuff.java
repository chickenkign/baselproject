package com.example.baselproject.DataXAdapters;

public class HomeStuff {
    private String name ;
    private String Description;
    private String image ;

    public HomeStuff() {

    }

    public HomeStuff(String name, String describtion, String image) {
        this.name = name;
        Description = describtion;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "HomeStuff{" +
                "name='" + name + '\'' +
                ", Describtion='" + Description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
