package com.oskarlund.musicapi.web;


import com.oskarlund.musicapi.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;

import static com.oskarlund.musicapi.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
	classes = MusicApiApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String URL = "http://localhost:%d/restapi/artist/%s";

	@Test
	void getArtist_Nirvana() {
		String endpoint = String.format(URL, port, NIRVANA_MBID);
		ResponseJson nirvana = this.restTemplate.getForObject(endpoint, ResponseJson.class);
		assertEquals("Nirvana", nirvana.getName());

		Optional<Album> nevermind = nirvana.getAlbums().stream().filter(a -> a.getTitle().equals("Nevermind")).findFirst();
		assertTrue(nevermind.isPresent());
	}

	@Test
	void getArtist_MJ() {
		String endpoint = String.format(URL, port, MJ_MBID);
		ResponseJson mj = this.restTemplate.getForObject(endpoint, ResponseJson.class);

		assertEquals(25, mj.getAlbums().size());
		Optional<Album> thriller = mj.getAlbums().stream().filter(a -> a.getTitle().equals("Thriller")).findFirst();

		assertTrue(thriller.isPresent());
	}
}