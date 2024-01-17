package com.application.music.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.music.entity.Song;
import com.application.music.repository.MusicRepository;
import com.application.music.services.StorageService;

@RestController
@RequestMapping("/api/songs")
public class SongController {
	
	private final StorageService storageService;
	private final MusicRepository musicRepository;
	
	public SongController(StorageService storageService, MusicRepository musicRepository) {
		super();
		this.storageService = storageService;
		this.musicRepository = musicRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Song>> getSongs(){
		
		return ResponseEntity.ok(musicRepository.findAll());
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Song> getSong(@PathVariable String id){
		
		Optional<Song> song = musicRepository.findById(id);
		if(song.isPresent()) {
			return ResponseEntity.ok(song.get());

		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<?> createSongs(@RequestPart("song")Song song, @RequestPart("file") MultipartFile file){
		
		if(musicRepository.existsSongByFileNameEquals(file.getOriginalFilename())|| musicRepository.existsSongByTitleEquals(song.getTitle())) {
			return ResponseEntity.badRequest().body("taken");
		}else {
			storageService.uploadSongs(file);
			song.setFileName(file.getOriginalFilename());
			
			Song uploadedSong= musicRepository.insert(song);
			
			return new ResponseEntity<>(uploadedSong,HttpStatus.CREATED);
		}
		
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Song> updateSong(@PathVariable String id, @RequestBody Song songData ){
		
	 Optional<Song> songFound = musicRepository.findById(id);
		if(songFound.isPresent()) {
			Song song= songFound.get();
			
			if(songData.getTitle()!=null) {
				song.setTitle(songData.getTitle());
			}
			if(songData.getArtist()!=null) {
				song.setArtist(song.getArtist());
			}
			musicRepository.save(song);
			return ResponseEntity.ok(song);
		}
		else {
			return ResponseEntity.notFound().build();
			
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Song> deleteSong(@PathVariable String id){
		
		 Optional<Song> song = musicRepository.findById(id);
		 if(song.isPresent()) {
			 musicRepository.deleteById(id);
			 
			 return ResponseEntity.noContent().build();
		 }else {
				return ResponseEntity.notFound().build();

		 }
		
	}
}
