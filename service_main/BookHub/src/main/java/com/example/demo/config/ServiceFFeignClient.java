package com.example.demo.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name= "service-f-api", url="http://localhost:8003")
public interface ServiceFFeignClient {
	
	@GetMapping("/contador")
	public int getContador();

}
