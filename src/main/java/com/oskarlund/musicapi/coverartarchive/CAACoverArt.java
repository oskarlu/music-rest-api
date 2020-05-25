package com.oskarlund.musicapi.coverartarchive;


import java.util.Collection;
import java.util.Objects;


public class CAACoverArt {

	private String id;
	private Collection<CAAImage> images;

	public CAACoverArt() {}

	public CAACoverArt(String id, Collection<CAAImage> images) {
		this.id = id;
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public Collection<CAAImage> getImages() {
		return images;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CAACoverArt{" +
			"id='" + id + '\'' +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CAACoverArt that = (CAACoverArt) o;
		return id.equals(that.id) &&
			Objects.equals(images, that.images);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, images);
	}
}
