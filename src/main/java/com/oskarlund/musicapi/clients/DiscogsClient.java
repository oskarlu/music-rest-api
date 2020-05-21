package com.oskarlund.musicapi.clients;


import com.oskarlund.musicapi.clients.dtos.DiscogsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "DiscogsClient", url = "https://api.discogs.com/")
public interface DiscogsClient {

	// MJ: https://api.discogs.com/artists/15885

	@GetMapping(value = "artists/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	DiscogsDto getArtist(final @PathVariable("id") String id);
}
