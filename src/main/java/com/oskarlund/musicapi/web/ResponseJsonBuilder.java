package com.oskarlund.musicapi.web;


import com.oskarlund.musicapi.coverartarchive.CAACoverArt;
import com.oskarlund.musicapi.coverartarchive.CAAImage;
import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.musicbrainz.MBReleaseGroup;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;


public class ResponseJsonBuilder {

	private String mbid;
	private String name;
	private String description;

	/**
	 * Keeping albums in a Map for faster lookup when adding cover art.
	 */
	private Map<String, Album> albums;

	private ResponseJsonBuilder() {	}

	public static ResponseJsonBuilder create() {
		return new ResponseJsonBuilder();
	}

	public ResponseJsonBuilder artist(MBArtist artist) {
		this.mbid = artist.getId();
		this.name = artist.getName();
		return this.albums(artist.getReleaseGroups());
	}

	public ResponseJsonBuilder mbid(String mbid) {
		this.mbid = mbid;
		return this;
	}

	public ResponseJsonBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ResponseJsonBuilder description(String description) {
		this.description = description;
		return this;
	}

	public ResponseJsonBuilder albums(Collection<MBReleaseGroup> releaseGroups) {
		// Keeping albums in a Map for faster lookup when adding cover art later.
		this.albums = releaseGroups.stream()
			.map(rg -> new Album(rg.getId(), rg.getTitle()))
			.collect(Collectors.toMap(Album::getId, identity()));
		return this;
	}

	public void cover(CAACoverArt cover) {

		// This could be inlined but kept intermediate var for readability
		String image = cover.getImages().stream()
			.filter(CAAImage::isFront) // This filters to only front cover image
			.map(CAAImage::getImage)
			.findFirst().orElse("<no cover image>");

		albums.computeIfPresent(cover.getId(), (k, v) -> {
			v.setImage(image);
			return v;
		});
	}

	public ResponseJson build() {
		return new ResponseJson(mbid, name, description, albums.values());
	}
}