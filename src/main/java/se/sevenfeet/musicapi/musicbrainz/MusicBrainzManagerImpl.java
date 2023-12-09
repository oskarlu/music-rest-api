package se.sevenfeet.musicapi.musicbrainz;


import se.sevenfeet.musicapi.MusicBrainzManager;
import se.sevenfeet.musicapi.errorhandling.MusicBrainzException;
import feign.FeignException;
import org.springframework.http.HttpStatus;


public class MusicBrainzManagerImpl implements MusicBrainzManager {

	private final MusicBrainzClient client;

	public MusicBrainzManagerImpl(MusicBrainzClient client) {
		this.client = client;
	}

	@Override
	public MBArtist getArtist(String mbid) throws MusicBrainzException {
		try {
			return client.fetchArtist(mbid);
		}

		// Anything going wrong when requesting artist info from MusicBrainz IS catastrophic.
		catch (FeignException e) {
			throw new MusicBrainzException(HttpStatus.resolve(e.status()), "MusicBrainz responded with: " + e.getMessage(), e);
		}
		catch (Exception e) {
			throw new MusicBrainzException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong ;) ;) ;) ;) ;)", e);
		}
	}
}
