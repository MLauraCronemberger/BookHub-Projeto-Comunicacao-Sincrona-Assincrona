package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthorNewsService {
	
	private final List<AuthorNews> noticias_adaptacoes = new ArrayList<>();
	
	public void salvar_adaptacao (AuthorNews nova_noticia) {
		noticias_adaptacoes.add(nova_noticia);
	}
	
	public List<AuthorNews> listar_noticias(){
	    return noticias_adaptacoes;
	}

}