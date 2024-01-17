package com.application.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.application.music.services.StorageService;


@SpringBootApplication
public class MyMusicalWorldApplication {

//	@Autowired
//	private static StorageService service;
	
	public static void main(String[] args) {
	ApplicationContext context =SpringApplication.run(MyMusicalWorldApplication.class, args);
	
	StorageService storageservice = context.getBean(StorageService.class);
	System.out.println(storageservice.getSongFileNames());
	
	}

}
