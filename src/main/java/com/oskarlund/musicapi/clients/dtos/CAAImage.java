package com.oskarlund.musicapi.clients.dtos;


public class CAAImage {

	private long id;
	private boolean front;
	private String image;

	public CAAImage() {	}

	public long getId() {
		return id;
	}

	public boolean isFront() {
		return front;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "CAAImage{" +
			"id=" + id +
			", front=" + front +
			", image='" + image + '\'' +
			'}';
	}
}
