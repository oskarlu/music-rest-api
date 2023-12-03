package com.oskarlund.musicapi;


import com.oskarlund.musicapi.coverartarchive.CAACoverArt;
import com.oskarlund.musicapi.coverartarchive.CAAImage;
import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.musicbrainz.MBRelations;
import com.oskarlund.musicapi.musicbrainz.MBReleaseGroup;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TestDataConstants {

	public static final String NIRVANA_MBID = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";
	public static final String MJ_MBID = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";

	public static final FeignException.NotFound MB_NOT_FOUND = new FeignException.NotFound(
			"Not found in MusicBrainz",
//			Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", List.of("")), Request.Body.empty()), // OLD
			Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", List.of("")), Request.Body.empty(), new RequestTemplate()), // NEW
			null);
	public static final FeignException.BadRequest MB_BAD_REQUEST = new FeignException.BadRequest(
			"MusicBrainz says Bad request",
//			Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", List.of("")), Request.Body.empty()), // OLD
			Request.create(Request.HttpMethod.GET, "the url", Collections.singletonMap("", List.of("")), Request.Body.empty(), new RequestTemplate()), // NEW
			null);

	public static final String NEVERMIND_CAA_ID = "1b022e01-4da6-387b-8658-8678046e4cef";
	public static final String THE_VERY_BEST_CAA_ID = "5bcaeba6-a532-3fa0-b540-75c09f70f759";

	public static final MBArtist NIRVANA = new MBArtist("5b11f4ce-a62d-471e-81fc-a69a8278c7da", "Nirvana",
		Arrays.asList(
			new MBReleaseGroup(NEVERMIND_CAA_ID, "Never Mind", "some date"),
			new MBReleaseGroup(THE_VERY_BEST_CAA_ID, "The Very Best", "some other date")
		),
		Arrays.asList(
			new MBRelations("Spotify", Collections.singletonMap("resource", "https://open.spotify.com/artist/3fMbdgg4jU18AjLCKBhRSm")),
			new MBRelations("discogs", Collections.singletonMap("resource", "https://www.discogs.com/artist/15885"))
		));

	public static final CAACoverArt NEVERMIND = new CAACoverArt(NEVERMIND_CAA_ID,
		Arrays.asList(
			new CAAImage(true, "nevermind_front.jpg"),
			new CAAImage(false, "nevermind_other1.jpg"),
			new CAAImage(false, "nevermind_other2.jpg")
		));

	public static final CAACoverArt THE_VERY_BEST = new CAACoverArt("5bcaeba6-a532-3fa0-b540-75c09f70f759",
		Arrays.asList(
			new CAAImage(true, "the_very_best_front.jpg"),
			new CAAImage(false, "the_very_best_other1.jpg"),
			new CAAImage(false, "the_very_best_other2.jpg")
		));


}
