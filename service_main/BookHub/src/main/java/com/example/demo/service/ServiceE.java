package com.example.demo.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.config.ServiceEFeignClient;
import com.example.demo.domain.serviceE.AuthorNews;

@Service
public class ServiceE {
    @Value("${exchange.name.serviceE}")
    private String exchangeName;

    @Value("${routing.key.serviceE}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    
	private final ServiceEFeignClient Eclient;
    
    public ServiceE(RabbitTemplate rabbitTemplate, ServiceEFeignClient Eclient) {
        this.rabbitTemplate = rabbitTemplate;
        this.Eclient = Eclient;
    }
    
    public void enviarParaFila(AuthorNews nova_noticia) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, nova_noticia);
        System.out.println("Nova noticia do autor " + nova_noticia.getAutor()+ " enviada para RabbitMQ!");
    }
    
	public List<AuthorNews> listar_noticias(){
		return Eclient.listar_noticias();
	}
}
