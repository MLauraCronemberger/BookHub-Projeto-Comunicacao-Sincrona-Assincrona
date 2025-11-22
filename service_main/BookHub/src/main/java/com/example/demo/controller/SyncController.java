package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.servicec.InfoLivroResponse;
import com.example.demo.service.ServiceC;

@RestController
@RequestMapping("/sync")
public class SyncController {
	
	private final ServiceC service;

    public SyncController(ServiceC service) {
        this.service = service;
    }
    
    @GetMapping("/infos-livro/{livro_id}")
    public InfoLivroResponse buscarInfosSincrona(@PathVariable String livro_id) {
    	return service.buscarInfosSincrona(livro_id);
    }
}
