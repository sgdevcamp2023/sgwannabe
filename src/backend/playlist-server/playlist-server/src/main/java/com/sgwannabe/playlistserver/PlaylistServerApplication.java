package com.sgwannabe.playlistserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PlaylistServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistServerApplication.class, args);
	}

}
