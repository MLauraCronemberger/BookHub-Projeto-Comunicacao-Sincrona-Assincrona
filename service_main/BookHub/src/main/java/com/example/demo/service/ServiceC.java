package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.config.CFeignClient;
import com.example.demo.domain.servicec.InfoLivroResponse;

@Service
public class ServiceC {
	
	private final CFeignClient Cclient;
	
	public ServiceC(CFeignClient Cclient) {
		this.Cclient = Cclient;
	}
	
    public InfoLivroResponse buscarInfosSincrona(String livroId) {
        return Cclient.get_dados_sincronos(livroId);
    }

}
