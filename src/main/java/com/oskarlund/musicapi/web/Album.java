package com.oskarlund.musicapi.web;


import java.util.Objects;


public class Album {

	private final String id;
	private final String title;
	private String image;

	public Album(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "AlbumDto{" +
			"id='" + id + '\'' +
			", title='" + title + '\'' +
			", image='" + image + '\'' +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Album album = (Album) o;
		return id.equals(album.id) &&
			title.equals(album.title) &&
			Objects.equals(image, album.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, image);
	}
}
