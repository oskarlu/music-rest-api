package com.oskarlund.musicapi;


import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.web.ResponseJsonBuilder;

import java.util.concurrent.Future;


public interface CoverArtManager {

	Future<Void> fetchCoverArtAsync(MBArtist artist, ResponseJsonBuilder responseBuilder);
}
