package com.application.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.application.music.services.StorageService;

@Controller
public class IndexController {

	
	private final StorageService storageService;
	
	public IndexController(StorageService storageService) {
		
		this.storageService=storageService;
	}
	
	@GetMapping
	public String getHomepage(Model model) {
		
		
		model.addAttribute("songFileNames",storageService.getSongFileNames());
		return "index";
	}
	
	
	@PostMapping
	public String handleFileUpload(@RequestParam("file")MultipartFile file) {
		
		storageService.uploadSongs(file);
		return "redirect:/";
	}
	
}
