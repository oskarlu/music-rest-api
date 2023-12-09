package se.sevenfeet.musicapi;

import se.sevenfeet.musicapi.coverartarchive.CoverArtArchiveClient;
import se.sevenfeet.musicapi.coverartarchive.CoverArtManagerImpl;
import se.sevenfeet.musicapi.discogs.DiscogsClient;
import se.sevenfeet.musicapi.discogs.DiscogsDescriptionManager;
import se.sevenfeet.musicapi.musicbrainz.MusicBrainzClient;
import se.sevenfeet.musicapi.musicbrainz.MusicBrainzManagerImpl;
import feign.Logger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition
@Configuration
public class MusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApiApplication.class, args);
	}

	@Bean
	MusicBrainzManager musicBrainzManager(MusicBrainzClient client) {
		return new MusicBrainzManagerImpl(client);
	}

	@Bean
	CoverArtManager coverArtManager(CoverArtArchiveClient client) {
		return new CoverArtManagerImpl(client);
	}

	@Bean
	DescriptionManager descriptionManager(DiscogsClient client) {
		return new DiscogsDescriptionManager(client);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.NONE;
	}
}