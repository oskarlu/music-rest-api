package se.sevenfeet.musicapi.musicbrainz;


import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MBReleaseGroup that = (MBReleaseGroup) o;
        return id.equals(that.id) &&
            title.equals(that.title) &&
            firstReleaseDate.equals(that.firstReleaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, firstReleaseDate);
    }
}
