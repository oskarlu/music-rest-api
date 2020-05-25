package com.oskarlund.musicapi.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class ResponseJson {

	private String mbid;
	private String name;
	private String description;
	private List<Album> albums;

	public ResponseJson() { }

	public ResponseJson(String mbid, String name, String description, Collection<Album> albums) {
		this.mbid = mbid;
		this.name = name;
		this.description = description;
		this.albums = new ArrayList<>(albums);
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

	public List<Album> getAlbums() {
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

	public static class Error {
		private final String error;
		private final int status;

		public Error(String error, int status) {
			this.error = error;
			this.status = status;
		}

		public String getError() {
			return error;
		}

		public int getStatus() {
			return status;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ResponseJson that = (ResponseJson) o;
		return mbid.equals(that.mbid) &&
			name.equals(that.name) &&
			Objects.equals(description, that.description) &&
			Objects.equals(albums, that.albums);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mbid, name, description, albums);
	}
}
