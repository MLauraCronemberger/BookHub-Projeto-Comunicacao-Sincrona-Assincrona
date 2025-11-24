package com.example.demo.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.serviced.MediaAdaptation;

@FeignClient(name= "service-d-api", url="http://localhost:8001")
public interface ServiceDFeignClient { 
	
	@GetMapping("/adaptacoes/listar")
	List<MediaAdaptation> listar_noticias(); 
	
}