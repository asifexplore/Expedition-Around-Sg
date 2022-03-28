package com.example.a15017498.touraroundsg_;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15017498 on 11/1/2017.
 */

public class BlogDeveloper {

    private String title,desc,post_name,uid,Location_desc,post_image;
    public Map<String, Boolean> stars = new HashMap<>();


    public BlogDeveloper(){

    }

    public BlogDeveloper(String title, String desc, String post_name,String uid,String Location_desc,String post_image) {
        this.title = title;
        this.desc = desc;
        this.post_name = post_name;
        this.uid=uid;
        this.Location_desc = Location_desc;
        this.post_image = post_image;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getLocation_desc() {
        return Location_desc;
    }

    public void setLocation_desc(String location_desc) {
        Location_desc = location_desc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("title", title);
//        result.put("desc",desc);
//        result.put("post_name",post_name);
//
//        return result;
//    }




}
