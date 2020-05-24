package com.oskarlund.musicapi.musicbrainz;


public class MBReleaseGroup {

    private String id;
    private String title;
    private String firstReleaseDate;

    public MBReleaseGroup() { }

    public MBReleaseGroup(String id, String title, String firstReleaseDate) {
        this.id = id;
        this.title = title;
        this.firstReleaseDate = firstReleaseDate;
    }

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
