package com.epam.bets.entity;

import java.time.LocalDate;

public class News extends Entity{
    private int id;
    private String title;
    private String text;
    private LocalDate date;
    private String pictureUrl;
    private final String DEFAULT_PICTURE = "defaultNews.jpg";

    public News() {
        pictureUrl = DEFAULT_PICTURE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
