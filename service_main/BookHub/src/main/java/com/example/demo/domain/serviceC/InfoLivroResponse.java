package com.example.demo.domain.serviceC;

public class InfoLivroResponse {
	
    private int livro_id;
    private DadosLivro dados_livro;
    private DadosAutor dados_autor;
    
    
	public int getLivro_id() {
		return livro_id;
	}
	public void setLivro_id(int livro_id) {
		this.livro_id = livro_id;
	}
	public DadosLivro getDados_livro() {
		return dados_livro;
	}
	public void setDados_livro(DadosLivro dados_livro) {
		this.dados_livro = dados_livro;
	}
	public DadosAutor getDados_autor() {
		return dados_autor;
	}
	public void setDados_autor(DadosAutor dados_autor) {
		this.dados_autor = dados_autor;
	} 

}
