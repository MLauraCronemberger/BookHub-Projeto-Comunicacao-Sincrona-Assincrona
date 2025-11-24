package com.example.demo.domain.serviceE;

public class AuthorNews {
	private int livro_id;
	private String autor;
	private String tipo_noticia;
	private String descricao;
	
	public int getLivro_id() {
		return livro_id;
	}
	public void setLivro_id(int livro_id) {
		this.livro_id = livro_id;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getTipo_noticia() {
		return tipo_noticia;
	}
	public void setTipo_noticia(String tipo_noticia) {
		this.tipo_noticia = tipo_noticia;
	}
	public String getDescricao() {
		return descricao;
	}
    public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

    public AuthorNews() {
    	
    }

	public AuthorNews(int livro_id, String autor, String tipo_noticia, String descricao) {
		super();
		this.livro_id = livro_id;
		this.autor = autor;
		this.tipo_noticia = tipo_noticia;
		this.descricao = descricao;
	}
}

