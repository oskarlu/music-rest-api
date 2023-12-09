package se.sevenfeet.musicapi.web;


import org.junit.jupiter.api.Test;

import java.util.Optional;

import static se.sevenfeet.musicapi.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.*;


class ResponseJsonBuilderTest {

	@Test
	void testArtistOnly() {
		ResponseJsonBuilder builder = ResponseJsonBuilder.create();
		builder
			.mbid(NIRVANA.getId())
			.name(NIRVANA.getName())
			.albums(NIRVANA.getReleaseGroups());

		ResponseJson responseJson = builder.build();
		assertNull(responseJson.getDescription());
		assertEquals(2, responseJson.getAlbums().size());
		responseJson.getAlbums().forEach(a -> assertNull(a.getImage())); // no cover art yet
	}

	@Test
	void testBuildFullResponse() {
		ResponseJsonBuilder builder = ResponseJsonBuilder.create();
		builder
			.artist(NIRVANA)
			.cover(NEVERMIND)
//			.cover(THE_VERY_BEST) // pretend we didn't find any
			.description("The artist profile description");

		ResponseJson responseJson = builder.build();
		assertNotNull(responseJson.getDescription());
		assertEquals(2, responseJson.getAlbums().size());

		Optional<Album> nevermind = responseJson.getAlbums().stream().filter(a -> a.getId().equals(NEVERMIND.getId())).findFirst();
		assertTrue(nevermind.isPresent());
		assertNotNull(nevermind.get().getImage()); // incl cover art

		Optional<Album> theverybest = responseJson.getAlbums().stream().filter(a -> a.getId().equals(THE_VERY_BEST.getId())).findFirst();
		assertTrue(theverybest.isPresent());
		assertNull(theverybest.get().getImage()); // no cover art was added

		// in reality "<no cover art>" is always added but testing for null works better in a test
	}
}