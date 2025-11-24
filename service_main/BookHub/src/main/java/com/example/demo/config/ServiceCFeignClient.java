package com.example.demo.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.serviceC.AllLivros;
import com.example.demo.domain.serviceC.InfoLivroResponse;

@FeignClient(name= "service-c-api", url="http://localhost:8000")
public interface ServiceCFeignClient {
	
	@GetMapping("/all-livros")
	List<AllLivros> get_lista_livros();
	
	@GetMapping("/infos-livro/{livro_id}")
	InfoLivroResponse get_dados_sincronos(@PathVariable("livro_id") int livro_id);
	

}
