package com.oskarlund.musicapi.web;


public class Album {

	private String id;
	private String title;
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
}
