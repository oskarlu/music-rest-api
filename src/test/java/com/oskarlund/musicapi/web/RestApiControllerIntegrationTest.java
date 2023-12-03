package com.oskarlund.musicapi.web;


import com.oskarlund.musicapi.MusicApiApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

import static com.oskarlund.musicapi.TestDataConstants.MJ_MBID;
import static com.oskarlund.musicapi.TestDataConstants.NIRVANA_MBID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

		assertEquals(25, nirvana.getAlbums().size());
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

	/**
	 * Submits a new request every 1010 ms for 10 seconds. If we request more often than
	 * 1/s the MusicBrainz and CAA apis are rate limiting us...
	 */
	@Test
	@Disabled
	void testWithSomeLoad() throws InterruptedException, ExecutionException {
		final long start = System.currentTimeMillis();
		String mjEndpoint = String.format(URL, port, MJ_MBID);
		String niEndpoint = String.format(URL, port, NIRVANA_MBID);

		ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);

		System.out.println("Scheduling requests...");
		ScheduledFuture<?> scheduledFuture = scheduledExecutor.scheduleAtFixedRate(() -> {

			Future<?> future = scheduledExecutor.submit(() -> {
				ResponseJson res;
				final long execAfterMs = System.currentTimeMillis() - start;
				final long localStartMs = System.currentTimeMillis();

				if (new Random().nextInt() % 2 == 0) {
					System.out.println("Requesting Jackson after " + execAfterMs);
					res = this.restTemplate.getForObject(mjEndpoint, ResponseJson.class);
				} else {
					System.out.println("Requesting Nirvana after " + execAfterMs);
					res = this.restTemplate.getForObject(niEndpoint, ResponseJson.class);
				}
				final long ms = System.currentTimeMillis() - localStartMs;
				System.out.println(ms + " ms to fetch " + res.getName());
			});
		}, 0L, 1010L, TimeUnit.MILLISECONDS);

		System.out.println("Waiting for 10 secs before shutting down...");
		Thread.sleep(11 * 1000L);

		System.out.println("Shutting down executor...");
		scheduledExecutor.shutdown();

		System.out.println("Await termination for up to 15 seconds...");
		scheduledExecutor.awaitTermination(15, TimeUnit.SECONDS);

		if (!scheduledExecutor.isTerminated()) {
			boolean cancelled = scheduledFuture.cancel(false);
			assertTrue(cancelled);
			scheduledExecutor.shutdownNow();
		}
	}
}