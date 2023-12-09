package se.sevenfeet.musicapi.musicbrainz;


import se.sevenfeet.musicapi.MusicBrainzManager;
import se.sevenfeet.musicapi.errorhandling.MusicBrainzException;
import org.junit.jupiter.api.Test;

import static se.sevenfeet.musicapi.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MusicBrainzManagerImplTest {

	@Test
	void getArtist_HappyCase() {
		MusicBrainzClient client = mock(MusicBrainzClient.class);
		when(client.fetchArtist(NIRVANA_MBID)).thenReturn(NIRVANA);
		MusicBrainzManager classUnderTest = new MusicBrainzManagerImpl(client);
		MBArtist response = classUnderTest.getArtist(NIRVANA_MBID);
		assertNotNull(response);
		assertEquals(NIRVANA.getName(), response.getName());
	}

	@Test
	void getArtist_Null_MBID() {
		MusicBrainzClient client = mock(MusicBrainzClient.class);
		when(client.fetchArtist(anyString())).thenReturn(NIRVANA);
		when(client.fetchArtist(isNull())).thenThrow(MB_BAD_REQUEST);
		MusicBrainzManager classUnderTest = new MusicBrainzManagerImpl(client);
		MBArtist nirvana = classUnderTest.getArtist(NIRVANA_MBID);
		assertNotNull(nirvana);

		try {
			classUnderTest.getArtist(null);
			fail("Should not get here");
		} catch (MusicBrainzException e) {
			assertEquals(400, e.getStatus().value());
			System.out.println("All good! Expected MBException thrown due to: " + e.getReason());
		}
	}

	@Test
	void getArtist_404_NotFound() {
		MusicBrainzClient client = mock(MusicBrainzClient.class);
		when(client.fetchArtist(anyString())).thenReturn(NIRVANA).thenThrow(MB_NOT_FOUND);
		MusicBrainzManager classUnderTest = new MusicBrainzManagerImpl(client);
		MBArtist nirvana = classUnderTest.getArtist(NIRVANA_MBID); // 1st call should work
		assertNotNull(nirvana);

		try {
			classUnderTest.getArtist(NIRVANA_MBID); // mock set to throw 404 on 2nd call
			fail("Should not get here");
		} catch (MusicBrainzException e) {
			assertEquals(404, e.getStatus().value());
			System.out.println("All good! Expected MBException thrown due to: " + e.getReason());
		}
	}
}