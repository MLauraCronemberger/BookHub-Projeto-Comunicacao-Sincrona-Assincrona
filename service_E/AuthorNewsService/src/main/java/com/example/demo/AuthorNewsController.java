package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noticias-autores")
public class AuthorNewsController {
	
	@Autowired
	private AuthorNewsService service;
	
	@GetMapping("/listar")
	public List<AuthorNews> listar_noticias(){
		return service.listar_noticias();
	}

}
