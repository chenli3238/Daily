package com.wqy.daily.model;

import java.util.Date;
import java.util.List;

/**
 * Created by wqy on 17-2-6.
 */

public class Diary {

    private Date date;

    private String text;

    private List<String> pictures;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
