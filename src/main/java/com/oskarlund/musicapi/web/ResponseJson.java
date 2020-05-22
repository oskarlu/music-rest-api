package com.oskarlund.musicapi.web;


import java.util.Collection;


public class ResponseJson {

	private String mbid;
	private String name;
	private String description;
	private Collection<Album> albums;

	public ResponseJson() { }

	public ResponseJson(String mbid, String name, String description, Collection<Album> albums) {
		this.mbid = mbid;
		this.name = name;
		this.description = description;
		this.albums = albums;
	}

	public String getMbid() {
		return mbid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Collection<Album> getAlbums() {
		return albums;
	}

	@Override
	public String toString() {
		return "ResponseJsonDto{" +
			"mbid='" + mbid + '\'' +
			", name='" + name + '\'' +
			", description='" + description + '\'' +
			", albums=" + albums +
			'}';
	}
}
