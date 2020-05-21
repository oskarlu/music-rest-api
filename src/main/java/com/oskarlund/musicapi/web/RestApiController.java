package com.oskarlund.musicapi.web;

import com.oskarlund.musicapi.clients.MusicBrainzClient;
import com.oskarlund.musicapi.clients.dtos.MBArtist;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/restapi")
public class RestApiController {

    private MusicBrainzClient musicBrainzClient;

    public RestApiController(MusicBrainzClient musicBrainzClient) {
        this.musicBrainzClient = musicBrainzClient;
    }

    @GetMapping("/artist/{mbid}")
    String getArtist(@PathVariable("mbid") String mbId) {
        System.out.println("Request start. MBID = _" +mbId+"_");

        String nirvana = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";
        String mj = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";


        mbId = mbId.isEmpty() ? nirvana : mbId;

        MBArtist res = musicBrainzClient.getArtist(mbId);

        System.out.println("Artist name = " + res.getName());

        if (!res.getReleaseGroups().isEmpty()) {
            System.out.println("First Album = " + res.getReleaseGroups().get(0).getTitle());
            System.out.println("Total albums: " + res.getReleaseGroups().size());
        }

        System.out.println(res.toString());

        System.out.println("Request end.");
        return String.format("You want info on artist with MBID %s", mbId);
    }
}
