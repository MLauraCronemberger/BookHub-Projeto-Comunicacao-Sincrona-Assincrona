package com.example.demo.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.servicec.InfoLivroResponse;

@FeignClient(name= "service-c-api", url="http://localhost:8000")
public interface CFeignClient {
	
	@GetMapping("/all-infos-livro/{livro_id}")
	InfoLivroResponse get_dados_sincronos(@PathVariable("livro_id") String livro_id);
	

}
