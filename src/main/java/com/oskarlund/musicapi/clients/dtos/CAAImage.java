package com.oskarlund.musicapi.clients.dtos;


public class CAAImage {

	private boolean front;
	private String image;

	public CAAImage() {	}

	public boolean isFront() {
		return front;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "CAAImage{" +
			"front=" + front +
			", image='" + image + '\'' +
			'}';
	}
}
