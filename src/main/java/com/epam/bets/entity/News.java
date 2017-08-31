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
        if(!pictureUrl.isEmpty()) {
            this.pictureUrl = pictureUrl;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (text != null ? !text.equals(news.text) : news.text != null) return false;
        if (date != null ? !date.equals(news.date) : news.date != null) return false;
        return pictureUrl != null ? pictureUrl.equals(news.pictureUrl) : news.pictureUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
