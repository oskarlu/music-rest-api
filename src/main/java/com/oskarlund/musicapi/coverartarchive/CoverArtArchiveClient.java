package com.oskarlund.musicapi.coverartarchive;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "CoverArtArchiveClient", url = "https://coverartarchive.org")
public interface CoverArtArchiveClient {

	@GetMapping(value = "/release-group/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	CAACoverArt fetchCoverArt(final @PathVariable("id") String id);
}

/*
    MJ Thriller:
    https://coverartarchive.org/release-group/f32fab67-77dd-3937-addc-9062e28e4c37

	MJ Got To Be Here:
	https://coverartarchive.org/release-group/97e0014d-a267-33a0-a868-bb4e2552918a
*/