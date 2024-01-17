package com.application.music.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.application.music.entity.Song;

@Repository
public interface MusicRepository extends MongoRepository<Song, String> {

	boolean existsSongByFileNameEquals(String songName);
	boolean existsSongByTitleEquals(String title);
}
