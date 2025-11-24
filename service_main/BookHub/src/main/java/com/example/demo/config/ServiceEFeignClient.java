package com.example.demo.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.serviceE.AuthorNews;

@FeignClient(name= "service-e-api", url="http://localhost:8002")
public interface ServiceEFeignClient {
	
	@GetMapping("/noticias-autores/listar")
	public List<AuthorNews> listar_noticias();

}
