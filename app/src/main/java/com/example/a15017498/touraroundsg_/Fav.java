package com.example.a15017498.touraroundsg_;

/**
 * Created by 15017498 on 11/1/2017.
 */

public class Fav {
    private String post_id;
    private String status;
    private String ui_id;

    public Fav(String post_id, String status, String ui_id) {
        this.post_id = post_id;
        this.status = status;
        this.ui_id = ui_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUi_id() {
        return ui_id;
    }

    public void setUi_id(String ui_id) {
        this.ui_id = ui_id;
    }
}
