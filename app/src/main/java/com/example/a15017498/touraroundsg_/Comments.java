package com.example.a15017498.touraroundsg_;

/**
 * Created by 15017498 on 9/1/2017.
 */


public class Comments {
    private int id = 0;
    private String name = "";
    private String comment_given = "";

    public Comments(){

    }

    public Comments(int id, String name, String comment_given) {
        this.name = name;
        this.comment_given = comment_given;
        this.id = id;
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

    public String getComment_given() {
        return comment_given;
    }

    public void setComment_given(String comment_given) {
        this.comment_given = comment_given;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "name='" + name + '\'' +
                ", comment_given='" + comment_given + '\'' +
                '}';
    }
}
