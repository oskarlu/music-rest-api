package com.oskarlund.musicapi.discogs;


import com.oskarlund.musicapi.DescriptionManager;
import com.oskarlund.musicapi.musicbrainz.MBRelations;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class DiscogsDescriptionManager implements DescriptionManager {

	Logger LOG = LoggerFactory.getLogger(DiscogsDescriptionManager.class);

	private final DiscogsClient discogsClient;

	public DiscogsDescriptionManager(DiscogsClient discogsClient) {
		this.discogsClient = discogsClient;
	}

	@Override
	public String fetchDescription(List<MBRelations> relations) {
		Optional<MBRelations> discogs = relations.stream()
			.filter(e -> e.getType().equals("discogs")).findFirst();

		if (discogs.isPresent()) {
			try {
				String url = discogs.get().getUrl().get("resource");
				String artistId = url.substring(url.lastIndexOf("/"));
				DiscogsDto dto = discogsClient.getArtist(artistId);
				return dto.getProfile();
			} catch (FeignException e) {
				// FeignClient exceptions will be due to external api response so not much we can do about it, hence just "warn"
				LOG.warn("Failed to get description due to \"{}\"", e.getMessage());
			} catch (Exception e) {
				LOG.error("Exception thrown when getting artist description.", e);
				return "Error when getting artist description from Discogs";
			}
		}
		return "No description found on Discogs";
	}
}
