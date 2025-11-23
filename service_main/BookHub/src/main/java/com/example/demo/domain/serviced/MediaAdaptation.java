package com.example.demo.domain.serviced;

public class MediaAdaptation {
	private int livro_id;
	private String titulo;
	private String tipo_adaptacao;
	private String descricao;
	
    public MediaAdaptation() {
    }
	
	public int getLivro_id() {
		return livro_id;
	}
	public void setLivro_id(int livro_id) {
		this.livro_id = livro_id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTipo_adaptacao() {
		return tipo_adaptacao;
	}
	public void setTipo_adaptacao(String tipo_adaptacao) {
		this.tipo_adaptacao = tipo_adaptacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MediaAdaptation(int livro_id, String titulo, String tipo_adaptacao, String descricao) {
		super();
		this.livro_id = livro_id;
		this.titulo = titulo;
		this.tipo_adaptacao = tipo_adaptacao;
		this.descricao = descricao;
	}
	

	
	
	

}

