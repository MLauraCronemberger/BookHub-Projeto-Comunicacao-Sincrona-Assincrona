package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.servicec.AllLivros;
import com.example.demo.domain.servicec.InfoLivroResponse;
import com.example.demo.service.ServiceC;
import com.example.demo.service.ServiceF;

@RestController
@RequestMapping("/sync")
public class SyncController {
	
	private final ServiceC service;

    public SyncController(ServiceC service) {
        this.service = service;
    }
    
    @Autowired
    private ServiceF serviceF;
    
    @GetMapping("all-livros")
    public List<AllLivros> buscarTodosLivros(){
    	return service.buscarTodosLivros();
    }
    
    @GetMapping("/infos-livro/{livro_id}")
    public InfoLivroResponse buscarInfosSincrona(@PathVariable int livro_id) {
        serviceF.enviarParaFila();
        
    	return service.buscarInfosSincrona(livro_id);
    }
}
