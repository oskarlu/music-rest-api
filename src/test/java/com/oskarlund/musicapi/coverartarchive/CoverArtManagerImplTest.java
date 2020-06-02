package com.oskarlund.musicapi.coverartarchive;


import com.oskarlund.musicapi.web.ResponseJsonBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.oskarlund.musicapi.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;


class CoverArtManagerImplTest {

	@Test
	void testFetchCoverArtAsync_IsActuallyParallel() throws ExecutionException, InterruptedException {

		CoverArtArchiveClient clientMock = mock(CoverArtArchiveClient.class);

		doAnswer(new AnswersWithDelay(2000L, new Returns(NEVERMIND))).when(clientMock).fetchCoverArt(NEVERMIND_CAA_ID);
		doAnswer(new AnswersWithDelay(2000L, new Returns(THE_VERY_BEST))).when(clientMock).fetchCoverArt(THE_VERY_BEST_CAA_ID);

		ResponseJsonBuilder builder = ResponseJsonBuilder.create();
		builder.artist(NIRVANA);


		// **** Test method run ****
		Future<Void> future = new CoverArtManagerImpl(clientMock).fetchCoverArtAsync(NIRVANA, builder);


		long start = System.currentTimeMillis();
		future.get();
		long end = System.currentTimeMillis();
		long execTime = end - start;

		System.out.println("Waited " + execTime + " ms for future.get()");

		assertTrue(execTime < 3000L); // would be 4k+ if sequential
	}
}