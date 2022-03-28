package com.example.a15017498.touraroundsg_;

/**
 * Created by 15017498 on 10/1/2017.
 */

public class Blog {

    private String comment;
    private String u_id;
    private String username;
    private String post_key;




    public Blog(){

    }

    public Blog(String comment, String u_id) {
        this.comment = comment;
        this.u_id = u_id;
        this.username=username;
        this.post_key=post_key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost_key() {
        return post_key;
    }

    public void setPost_key(String post_key) {
        this.post_key = post_key;
    }

}
