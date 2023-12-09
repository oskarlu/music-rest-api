package com.oskarlund.musicapi.musicbrainz;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "MusicBrainzClient", url = "https://musicbrainz.org/ws/2")
public interface MusicBrainzClient {

    // Nirvana: http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json

    @GetMapping(value = "artist/{mbid}?inc=release-groups+url-rels&fmt=json", consumes = MediaType.APPLICATION_JSON_VALUE)
    MBArtist fetchArtist(final @PathVariable("mbid") String mbId);
}
