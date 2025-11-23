package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.serviced.MediaAdaptation;
import com.example.demo.service.ServiceD;

@RestController
@RequestMapping("/assync")
public class AssyncController {
	
	@Autowired
	private ServiceD serviceD;
	
//    public AssyncController(ServiceD serviceD) {
//        this.serviceD = serviceD;
//    }
	
	
    @PostMapping("/nova-adaptacao")
    public ResponseEntity<String> criarNoticia(@RequestBody MediaAdaptation nova_adaptacao) {
        serviceD.enviarParaFila(nova_adaptacao);
        return ResponseEntity.ok("Nova notícia de adaptacao do livro " + nova_adaptacao.getTitulo() + " enviada para a fila!");
    }
	
}
