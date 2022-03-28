package com.example.a15017498.touraroundsg_;

/**
 * Created by 15017498 on 8/1/2017.
 */

public class RowItem {

    private String title;
    private String desc;

    public RowItem(String title,String desc){
        this.title = title;
        this.desc=desc;

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

    @Override
    public String toString() {
        return "RowItem{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
