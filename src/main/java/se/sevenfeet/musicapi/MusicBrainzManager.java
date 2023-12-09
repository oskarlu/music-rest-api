package se.sevenfeet.musicapi;


import se.sevenfeet.musicapi.errorhandling.MusicBrainzException;
import se.sevenfeet.musicapi.musicbrainz.MBArtist;


public interface MusicBrainzManager {

	MBArtist getArtist(String mbid) throws MusicBrainzException;
}
