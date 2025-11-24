package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.serviceE.AuthorNews;
import com.example.demo.domain.serviced.MediaAdaptation;
import com.example.demo.service.ServiceD;
import com.example.demo.service.ServiceE;

@RestController
@RequestMapping("/assync")
public class AssyncController {
	
	@Autowired
	private ServiceD serviceD;
	
	@Autowired
	private ServiceE serviceE;
	
    @PostMapping("/nova-adaptacao")
    public ResponseEntity<String> criarNoticia(@RequestBody MediaAdaptation nova_adaptacao) {
        serviceD.enviarParaFila(nova_adaptacao);
        return ResponseEntity.ok("Nova notícia de adaptacao do livro " + nova_adaptacao.getTitulo() + " enviada para a fila!");
    }
    
    @GetMapping("/todas-adaptacoes-registradas")
	public List<MediaAdaptation> listar_adaptacoes(){
    	return serviceD.listar_noticias();
    }
    
    @PostMapping("/nova-noticia-autor")
    public ResponseEntity<String> criarNoticia(@RequestBody AuthorNews nova_noticia) {
        serviceE.enviarParaFila(nova_noticia);
        return ResponseEntity.ok("Nova notícia do autor " + nova_noticia.getAutor() + " enviada para a fila!");
    }
    
    @GetMapping("/todas-noticias-registradas")
    public List<AuthorNews> listar_noticias(){
    	return serviceE.listar_noticias();
    }
	
}
