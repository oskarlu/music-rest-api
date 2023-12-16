package se.sevenfeet.musicapi.coverartarchive;


import se.sevenfeet.musicapi.CoverArtManager;
import se.sevenfeet.musicapi.musicbrainz.MBArtist;
import se.sevenfeet.musicapi.musicbrainz.MBReleaseGroup;
import se.sevenfeet.musicapi.web.ResponseJsonBuilder;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.*;


public class CoverArtManagerImpl implements CoverArtManager {

	private static final Logger LOG = LoggerFactory.getLogger(CoverArtManagerImpl.class);

	private final CoverArtArchiveClient client;
	private final ExecutorService executor;

	public CoverArtManagerImpl(CoverArtArchiveClient client) {
		this.client = client;

		// Makes queue FIFO instead of LIFO (as it is when no explicit executor)
		executor = Executors.newWorkStealingPool(ForkJoinPool.getCommonPoolParallelism());
	}

	@Override
	public Future<Void> fetchCoverArtAsync(MBArtist artist, ResponseJsonBuilder responseBuilder) {
		/*
		 * javadoc: "If any of the given CompletableFutures complete exceptionally, then
		 * the returned CompletableFuture also does so". So we make sure they never do by
		 * implementing .runAsync().exceptionally(<return empty cover>)
		 */

		return CompletableFuture.allOf(artist.getReleaseGroups().stream()
			.map(rg -> fetchCoverArt(rg, responseBuilder, executor)).toArray(CompletableFuture[]::new));
	}

	private CompletableFuture<Void> fetchCoverArt(MBReleaseGroup releaseGroup, ResponseJsonBuilder responseBuilder, ExecutorService executor) {
		return CompletableFuture.runAsync(
			() -> responseBuilder.cover(fetchCoverArt(releaseGroup)), executor)
			.exceptionally(ex -> {
				// This should catch CompletableFuture CompletionExceptions and all other errors that are
				// not FeignClient exceptions (which we handle explicitly when invoking 'client')
				LOG.error("Failed to get cover for '{}'.", releaseGroup.getTitle(), ex);
				CAACoverArt noCover = new CAACoverArt(releaseGroup.getId(), Collections.emptyList());
				responseBuilder.cover(noCover); // essentially returning "<no cover art>"
				return null; // == Void
			});
	}

	private CAACoverArt fetchCoverArt(MBReleaseGroup releaseGroup) {
		try {
			CAACoverArt cover = client.fetchCoverArt(releaseGroup.getId());
			cover.setId(releaseGroup.getId());  // since it's not in the api response but we need to merge this with the release-groups from MB
			return cover;
		} catch (FeignException e) {
			// Probably not even a warning since there will be a lot of exceptions due to missing art
			//LOG.warn("Failed to get cover for \"{}\" due to \"{}\"", releaseGroup.getTitle(), e.getMessage());
		}
		return new CAACoverArt(releaseGroup.getId(), Collections.emptyList()); // essentially returning "<no cover art>"
	}
}
