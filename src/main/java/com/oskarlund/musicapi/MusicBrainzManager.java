package com.oskarlund.musicapi;


import com.oskarlund.musicapi.exceptions.MusicBrainzException;
import com.oskarlund.musicapi.musicbrainz.MBArtist;


public interface MusicBrainzManager {

	MBArtist getArtist(String mbid) throws MusicBrainzException;
}
