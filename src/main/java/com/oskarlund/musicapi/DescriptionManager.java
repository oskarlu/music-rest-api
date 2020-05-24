package com.oskarlund.musicapi;


import com.oskarlund.musicapi.musicbrainz.MBRelations;

import java.util.List;


public interface DescriptionManager {

	String getDescription(List<MBRelations> relations);
}
