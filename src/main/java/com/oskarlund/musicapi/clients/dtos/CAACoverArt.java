package com.oskarlund.musicapi.clients.dtos;


import java.util.List;


public class CAACoverArt {

	private String release; // TODO:  delete, not needed in any way
	private List<CAAImage> images;

	public CAACoverArt() {}

	public String getRelease() {
		return release;
	}

	public List<CAAImage> getImages() {
		return images;
	}

	@Override
	public String toString() {
		return "CAACoverArt{" +
			"release='" + release + '\'' +
			", images=" + images +
			'}';
	}
}
