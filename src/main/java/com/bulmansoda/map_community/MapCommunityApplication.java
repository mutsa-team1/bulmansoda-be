package com.bulmansoda.map_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MapCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapCommunityApplication.class, args);
	}

}
