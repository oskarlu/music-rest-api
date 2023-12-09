package se.sevenfeet.musicapi;


import se.sevenfeet.musicapi.musicbrainz.MBRelations;

import java.util.List;


public interface DescriptionManager {

	String fetchDescription(List<MBRelations> relations);
}
