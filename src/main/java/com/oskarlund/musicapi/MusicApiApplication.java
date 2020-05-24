package com.oskarlund.musicapi;

import com.oskarlund.musicapi.coverartarchive.CoverArtArchiveClient;
import com.oskarlund.musicapi.discogs.DiscogsClient;
import com.oskarlund.musicapi.discogs.DiscogsDescriptionManager;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EnableFeignClients
@Configuration
public class MusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApiApplication.class, args);
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