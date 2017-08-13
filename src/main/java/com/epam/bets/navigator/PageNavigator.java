package com.epam.bets.navigator;

public class PageNavigator {
    private String pageUrl;
    private PageType type;

    public PageNavigator() {
    }

    public PageNavigator(String pageUrl, PageType type) {
        this.pageUrl = pageUrl;
        this.type = type;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageNavigator that = (PageNavigator) o;

        if (pageUrl != null ? !pageUrl.equals(that.pageUrl) : that.pageUrl != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = pageUrl != null ? pageUrl.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageNavigator{" +
                "pageUrl='" + pageUrl + '\'' +
                ", type=" + type +
                '}';
    }
}
