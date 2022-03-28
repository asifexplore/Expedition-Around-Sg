package com.example.a15017498.touraroundsg_;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15017498 on 13/1/2017.
 */

public class BlogPersonal {

    private String title,desc,image,user_id;

    public BlogPersonal(){

    }

    public BlogPersonal(String title, String desc, String image, String user_id) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
