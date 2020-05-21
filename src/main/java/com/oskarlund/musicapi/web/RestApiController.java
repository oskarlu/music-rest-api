package com.oskarlund.musicapi.web;

import com.oskarlund.musicapi.clients.CoverArtArchiveClient;
import com.oskarlund.musicapi.clients.DiscogsClient;
import com.oskarlund.musicapi.clients.MusicBrainzClient;
import com.oskarlund.musicapi.clients.dtos.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/restapi")
public class RestApiController {

    private final MusicBrainzClient musicBrainzClient;
    private CoverArtArchiveClient coverArtArchiveClient;
    private DiscogsClient discogsClient;

    public RestApiController(MusicBrainzClient musicBrainzClient, CoverArtArchiveClient coverArtArchiveClient, DiscogsClient discogsClient) {
        this.musicBrainzClient = musicBrainzClient;
        this.coverArtArchiveClient = coverArtArchiveClient;
        this.discogsClient = discogsClient;
    }

    @GetMapping(value = "/artist/{mbid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getArtist(@PathVariable("mbid") String mbId) {

        String nirvana = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";
        String mj = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";

        mbId = mbId.isEmpty() ? nirvana : mbId;

        MBArtist mb = musicBrainzClient.getArtist(mbId);

        String artId = mb.getReleaseGroups().get(0).getId();

        CAACoverArt art = coverArtArchiveClient.getCoverArt(artId);

        Optional<CAAImage> front = art.getImages().stream().filter(e -> e.isFront()).findFirst();


                    System.out.println((front.isPresent() ? front.get().getImage() : "no front cover found"));


        Optional<MBRelations> discogs = mb.getRelations().stream()
            .filter(e -> e.getType().equals("discogs")).findFirst();


        if (discogs.isPresent()) {

            String res = discogs.get().getUrl().get("resource");

            String artistId = res.substring(res.lastIndexOf("/"));

            DiscogsDto discogsDto = discogsClient.getArtist(artistId);

                        System.out.println(discogsDto.getProfile());

        }



        return ResponseEntity.ok(mb);
    }
}
