package se.sevenfeet.musicapi.web;


import se.sevenfeet.musicapi.CoverArtManager;
import se.sevenfeet.musicapi.DescriptionManager;
import se.sevenfeet.musicapi.MusicBrainzManager;
import se.sevenfeet.musicapi.musicbrainz.MBArtist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.Future;


@RestController
@RequestMapping("/restapi")
public class RestApiController {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiController.class);

    private final MusicBrainzManager musicBrainzManager;
    private final CoverArtManager coverArtManager;
    private final DescriptionManager descriptionManager;

    public RestApiController(MusicBrainzManager musicBrainzManager, CoverArtManager coverArtManager, DescriptionManager descriptionManager) {
        this.musicBrainzManager = musicBrainzManager;
        this.coverArtManager = coverArtManager;
        this.descriptionManager = descriptionManager;
    }

    @GetMapping(value = "/artist/{mbid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseJson> getArtist(@PathVariable("mbid") String mbId) throws ResponseStatusException {

        LOG.debug("*** request for artist with id {}", mbId);

        ResponseJsonBuilder responseBuilder = ResponseJsonBuilder.create();

        MBArtist artist = musicBrainzManager.getArtist(mbId);
        responseBuilder.artist(artist);


        // Fetching covers async and in parallel, so we can fetch description in the meantime
        Future<Void> covertArtFuture = coverArtManager.fetchCoverArtAsync(artist, responseBuilder);


        // We could have a DescriptionManager implementation for any of the artists relations. In our case it's Discogs.
        String description = descriptionManager.fetchDescription(artist.getRelations());
        responseBuilder.description(description);


        try {
            LOG.trace("BLOCKING on cover art Future to complete before we're done.");
            covertArtFuture.get(); // block here until all cover art has been fetched
        } catch (Exception e) {
            LOG.error("Exception when blocking to wait for cover art.", e);
            // Anything gone wrong with cover art is not catastrophic, so we still respond normally
        }

        return ResponseEntity.ok(responseBuilder.build());
    }
}
