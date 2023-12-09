package se.sevenfeet.musicapi;


import se.sevenfeet.musicapi.musicbrainz.MBArtist;
import se.sevenfeet.musicapi.web.ResponseJsonBuilder;

import java.util.concurrent.Future;


public interface CoverArtManager {

	Future<Void> fetchCoverArtAsync(MBArtist artist, ResponseJsonBuilder responseBuilder);
}
