package com.oskarlund.musicapi.clients.dtos;


import java.util.Map;


public class MBRelations {

	private String type;  // will filter on "discogs"
	private Map<String, String> url;

	public MBRelations() { }

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