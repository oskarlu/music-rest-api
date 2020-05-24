package com.oskarlund.musicapi.musicbrainz;

import java.util.ArrayList;
import java.util.List;

public class MBArtist {

    private String id;
    private String name;
    private List<MBReleaseGroup> releaseGroups = new ArrayList<>();
    private List<MBRelations> relations = new ArrayList<>();

    public MBArtist() { }

    public MBArtist(String id, String name, List<MBReleaseGroup> releaseGroups, List<MBRelations> relations) {
        this.id = id;
        this.name = name;
        this.releaseGroups = releaseGroups;
        this.relations = relations;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MBReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public List<MBRelations> getRelations() {
        return relations;
    }

    @Override
    public String toString() {
        return "MBArtist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", releaseGroups=" + releaseGroups +
                '}';
    }
}

/*
    Nirvana:
    http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=release-groups&fmt=json

    Response:
    {
        id: "5b11f4ce-a62d-471e-81fc-a69a8278c7da",
        name: "Nirvana",
        sort-name: "Nirvana",
        type-id: "e431f5f6-b5d2-343d-8b36-72607fffb74b",
        type: "Group",
        disambiguation: "90s US grunge band",
        gender: null,
        gender-id: null,
        country: "US",
        area: {
            disambiguation: "",
            id: "489ce91b-6658-3307-9877-795b68554c98",
            sort-name: "United States",
            name: "United States",
            iso-3166-1-codes: ["US"]
        },
        begin-area: {
            id: "a640b45c-c173-49b1-8030-973603e895b5",
            disambiguation: "",
            name: "Aberdeen",
            sort-name: "Aberdeen"
        },
        end-area: null,
        life-span: {
            ended: true,
            begin: "1988-01",
            end: "1994-04-05"
        },
        isnis: ["0000000123486830", "0000000123487390"],
        ipis: [ ]
    }
*/