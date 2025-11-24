package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.config.ServiceCFeignClient;
import com.example.demo.domain.servicec.AllLivros;
import com.example.demo.domain.servicec.InfoLivroResponse;

@Service
public class ServiceC {
	
	private final ServiceCFeignClient Cclient;
	
	public ServiceC(ServiceCFeignClient Cclient) {
		this.Cclient = Cclient;
	}
	
    public InfoLivroResponse buscarInfosSincrona(int livroId) {
        return Cclient.get_dados_sincronos(livroId);
    }
    
    public List<AllLivros> buscarTodosLivros(){
    	return Cclient.get_lista_livros();
    }

}
