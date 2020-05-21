package com.oskarlund.musicapi.clients;

import com.oskarlund.musicapi.clients.dtos.MBArtist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "MusicBrainzClient", url = "https://musicbrainz.org/ws/2")
public interface MusicBrainzClient {

    // TODO: for some unknown reason this class indents 8 spaces instead of 4 in IntelliJ and I can't figure out why. Gah!


        // Nirvana: http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json

        @GetMapping(value = "artist/{mbid}?inc=release-groups+url-rels&fmt=json", consumes = MediaType.APPLICATION_JSON_VALUE)
        MBArtist getArtist(final @PathVariable("mbid") String mbId);
}
