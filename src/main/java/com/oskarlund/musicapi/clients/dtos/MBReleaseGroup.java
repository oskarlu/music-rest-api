package com.oskarlund.musicapi.clients.dtos;


public class MBReleaseGroup {

    private String id;
    private String title;
    private String firstReleaseDate;

    public MBReleaseGroup() { }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    @Override
    public String toString() {
        return "MBReleaseGroup{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", firstReleaseDate=" + firstReleaseDate +
                '}';
    }
}
