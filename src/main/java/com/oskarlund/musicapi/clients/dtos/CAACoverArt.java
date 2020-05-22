package com.oskarlund.musicapi.clients.dtos;


import java.util.Collection;
import java.util.Collections;


public class CAACoverArt {

	private String id;
	private Collection<CAAImage> images;

	public CAACoverArt() {}

	public CAACoverArt(String id) {
		this.id = id;
		this.images = Collections.emptyList();
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
}
