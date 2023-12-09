package se.sevenfeet.musicapi.musicbrainz;


import java.util.Map;
import java.util.Objects;


public class MBRelations {

	private String type;  // to filter on "discogs"
	private Map<String, String> url;

	public MBRelations() { }

	public MBRelations(String type, Map<String, String> url) {
		this.type = type;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public Map<String, String> getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "MBRelations{" +
			"type='" + type + '\'' +
			", url=" + url +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MBRelations that = (MBRelations) o;
		return type.equals(that.type) &&
			Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, url);
	}
}

/*
{
	"attribute-values": {},
	"ended": false,
	"url": {
		"resource": "https://www.discogs.com/artist/15885",
		"id": "01d7a3f4-00f9-4343-8043-cc518c73cc7f"
	},
	"source-credit": "",
	"attributes": [],
	"type-id": "04a5b104-a4c2-4bac-99a1-7b837c37d9e4",
	"type": "discogs",
	"end": null,
	"target-type": "url",
	"direction": "forward",
	"attribute-ids": {},
	"target-credit": "",
	"begin": null
},
 */