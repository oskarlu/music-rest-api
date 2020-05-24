package com.oskarlund.musicapi.web;

import com.oskarlund.musicapi.DescriptionManager;
import com.oskarlund.musicapi.coverartarchive.CAACoverArt;
import com.oskarlund.musicapi.coverartarchive.CoverArtArchiveClient;
import com.oskarlund.musicapi.musicbrainz.MBArtist;
import com.oskarlund.musicapi.musicbrainz.MBReleaseGroup;
import com.oskarlund.musicapi.musicbrainz.MusicBrainzClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;


@RestController
@RequestMapping("/restapi")
public class RestApiController {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiController.class);

    private final MusicBrainzClient musicBrainzClient;
    private final CoverArtArchiveClient coverArtArchiveClient;
    private final DescriptionManager descriptionManager;

    public RestApiController(MusicBrainzClient musicBrainzClient, CoverArtArchiveClient coverArtArchiveClient, DescriptionManager descriptionManager) {
        this.musicBrainzClient = musicBrainzClient;
        this.coverArtArchiveClient = coverArtArchiveClient;
        this.descriptionManager = descriptionManager;
    }

    @GetMapping(value = "/artist/{mbid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseJson> getArtist(@PathVariable("mbid") String mbId) {

        ResponseJsonBuilder responseBuilder = ResponseJsonBuilder.create();

        MBArtist artist = musicBrainzClient.getArtist(mbId);
        responseBuilder.artist(artist);

        // Fetching covers async and in parallel so we can fetch description in the meantime
        Future<Void> covertArt = fetchCoverArt(artist, responseBuilder);

        
        // We could have a DescriptionManager implementation for any of the artists relations. In our case it's Discogs.
        String description = descriptionManager.getDescription(artist.getRelations());
        responseBuilder.description(description);


        try {
            LOG.trace("BLOCKING on cover art Future to complete before we're done.");
            covertArt.get(); // block here until all cover art has been fetched
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Exception when blocking to wait for cover art.", e);
        }

        return ResponseEntity.ok(responseBuilder.build());
    }

    public Future<Void> fetchCoverArt(MBArtist artist, ResponseJsonBuilder rb) {
        return CompletableFuture.allOf(artist.getReleaseGroups().stream()
            .map(rg -> fetchCoverArt(rg, rb)).toArray(CompletableFuture[]::new));
    }

    public Future<Void> fetchCoverArt(MBReleaseGroup rg, ResponseJsonBuilder responseBuilder) {
        return CompletableFuture.runAsync(() -> responseBuilder.cover(fetchCoverArt(rg)));
    }

    private CAACoverArt fetchCoverArt(MBReleaseGroup rg) {
        try {
            CAACoverArt cover = coverArtArchiveClient.getCoverArt(rg.getId());
            cover.setId(rg.getId());  // since it's not in the api response but we need to merge this with the release-groups from MB
            return cover;
        } catch (FeignException e) {
            // FeignClient exceptions will be due to external api response so not much we can do about it, hence just "warn"
            LOG.warn("Failed to get cover for \"{}\" due to \"{}\"", rg.getTitle(), e.getMessage());
        } catch (Exception e) {
            // Any other exceptions might be more serious, hence "error"
            LOG.error("Failed to get cover for \"{}\".", rg.getTitle(), e);
        }
        return new CAACoverArt(rg.getId()); // essentially returning "<no cover art>"
    }
}
