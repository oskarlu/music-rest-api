package se.sevenfeet.musicapi.coverartarchive;


import java.util.Objects;


public class CAAImage {

	private boolean front;
	private String image;

	public CAAImage() {	}

	public CAAImage(boolean front, String image) {
		this.front = front;
		this.image = image;
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
			"front=" + front +
			", image='" + image + '\'' +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CAAImage caaImage = (CAAImage) o;
		return front == caaImage.front &&
			image.equals(caaImage.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(front, image);
	}
}
