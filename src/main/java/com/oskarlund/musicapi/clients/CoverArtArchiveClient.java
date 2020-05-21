package com.oskarlund.musicapi.clients;


import com.oskarlund.musicapi.clients.dtos.CAACoverArt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "CoverArtArchiveClient", url = "https://coverartarchive.org")
public interface CoverArtArchiveClient {

	// MJ Thriller: https://coverartarchive.org/release-group/f32fab67-77dd-3937-addc-9062e28e4c37
    /*
    {
        "id": "f32fab67-77dd-3937-addc-9062e28e4c37",
        "title": "Thriller",
        "first-release-date": "1982-11-30"
    }
    */

	@GetMapping(value = "/release-group/{mbid}", consumes = MediaType.APPLICATION_JSON_VALUE)
	CAACoverArt getCoverArt(final @PathVariable("mbid") String mbId);

}
