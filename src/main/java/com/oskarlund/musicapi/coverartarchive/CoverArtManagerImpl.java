package com.oskarlund.musicapi.coverartarchive;


import com.oskarlund.musicapi.CoverArtManager;
import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.musicbrainz.MBReleaseGroup;
import com.oskarlund.musicapi.web.ResponseJsonBuilder;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class CoverArtManagerImpl implements CoverArtManager {

	private static final Logger LOG = LoggerFactory.getLogger(CoverArtManagerImpl.class);

	private final CoverArtArchiveClient client;

	public CoverArtManagerImpl(CoverArtArchiveClient client) {
		this.client = client;
	}

	@Override
	public Future<Void> fetchCoverArtAsync(MBArtist artist, ResponseJsonBuilder responseBuilder) {
		return CompletableFuture.allOf(artist.getReleaseGroups().stream()
			.map(rg -> fetchCoverArt(rg, responseBuilder)).toArray(CompletableFuture[]::new));
	}

	public Future<Void> fetchCoverArt(MBReleaseGroup releaseGroup, ResponseJsonBuilder responseBuilder) {
		return CompletableFuture.runAsync(() -> responseBuilder.cover(fetchCoverArt(releaseGroup)));
	}

	private CAACoverArt fetchCoverArt(MBReleaseGroup releaseGroup) {
		try {
			CAACoverArt cover = client.fetchCoverArt(releaseGroup.getId());
			cover.setId(releaseGroup.getId());  // since it's not in the api response but we need to merge this with the release-groups from MB
			return cover;
		} catch (FeignException e) {
			// Probably not even a warning since there will be a lot of exceptions due to missing art but will leave in for now...
			LOG.warn("Failed to get cover for \"{}\" due to \"{}\"", releaseGroup.getTitle(), e.getMessage());
		} catch (Exception e) {
			// Any other exceptions might be more serious, hence "error"
			LOG.error("Failed to get cover for \"{}\".", releaseGroup.getTitle(), e);
		}
		return new CAACoverArt(releaseGroup.getId(), Collections.emptyList()); // essentially returning "<no cover art>"
	}
}
