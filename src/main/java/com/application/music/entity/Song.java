package com.application.music.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Song {

	
	@Id
	private String id;
	
	private String title;
	
	private boolean isFavourited;
	
	private String artist;
	
	private String fileName;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isFavourited() {
		return isFavourited;
	}

	public void setFavourited(boolean isFavourited) {
		this.isFavourited = isFavourited;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
