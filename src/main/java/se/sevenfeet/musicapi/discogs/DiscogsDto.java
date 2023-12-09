package se.sevenfeet.musicapi.discogs;


public class DiscogsDto {

	private String name;
	private String profile;

	public DiscogsDto() { }

	public String getName() {
		return name;
	}

	public String getProfile() {
		return profile;
	}

	@Override
	public String toString() {
		return "DiscogsDto{" +
			"name='" + name + '\'' +
			", profile='" + profile + '\'' +
			'}';
	}
}
