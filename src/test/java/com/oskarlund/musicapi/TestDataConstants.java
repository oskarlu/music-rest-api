package com.oskarlund.musicapi;


import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.musicbrainz.MBRelations;
import com.oskarlund.musicapi.musicbrainz.MBReleaseGroup;
import feign.FeignException;
import feign.Request;

import java.util.Arrays;
import java.util.Collections;


public class TestDataConstants {

	public static final String NIRVANA_MBID = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";
	public static final MBArtist NIRVANA = new MBArtist("5b11f4ce-a62d-471e-81fc-a69a8278c7da", "Nirvana",
		Arrays.asList(
			new MBReleaseGroup("1b022e01-4da6-387b-8658-8678046e4cef", "Never Mind", "some date"),
			new MBReleaseGroup("5bcaeba6-a532-3fa0-b540-75c09f70f759", "The Very Best", "some other date")
		),
		Arrays.asList(
			new MBRelations("Spotify", Collections.singletonMap("resource", "https://open.spotify.com/artist/3fMbdgg4jU18AjLCKBhRSm")),
			new MBRelations("discogs", Collections.singletonMap("resource", "https://www.discogs.com/artist/15885"))
		));




	public static final FeignException.NotFound MB_NOT_FOUND = new FeignException.NotFound("Not found in MusicBrainz", Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", Arrays.asList("")), Request.Body.empty()), null);
	public static final FeignException.BadRequest MB_BAD_REQUEST = new FeignException.BadRequest("MusicBrainz says Bad request", Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", Arrays.asList("")), Request.Body.empty()), null);


}
