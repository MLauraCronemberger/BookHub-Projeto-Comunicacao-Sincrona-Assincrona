package com.example.demo.domain.serviceC;

import java.util.List;

public class DadosAutor {
	
    private String nome_autor;
    private int idade_autor;
    private String nacionalidade;
    private List<String> obras_conhecidas;
    
    
	public String getNome_autor() {
		return nome_autor;
	}
	public void setNome_autor(String nome_autor) {
		this.nome_autor = nome_autor;
	}
	public int getIdade_autor() {
		return idade_autor;
	}
	public void setIdade_autor(int idade_autor) {
		this.idade_autor = idade_autor;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public List<String> getObras_conhecidas() {
		return obras_conhecidas;
	}
	public void setObras_conhecidas(List<String> obras_conhecidas) {
		this.obras_conhecidas = obras_conhecidas;
	}
    
    

}
