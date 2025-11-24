package com.example.demo.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.config.ServiceDFeignClient;
import com.example.demo.domain.serviced.MediaAdaptation;

@Service
public class ServiceD {
	
    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    
	private final ServiceDFeignClient Dclient;
    
    public ServiceD(RabbitTemplate rabbitTemplate, ServiceDFeignClient Dclient) {
        this.rabbitTemplate = rabbitTemplate;
        this.Dclient = Dclient;
    }
    
    
    public void enviarParaFila(MediaAdaptation nova_adaptacao) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, nova_adaptacao);
        System.out.println("Nova adaptação do livro " + nova_adaptacao.getTitulo()+ " enviada para RabbitMQ!");
    }
    
	public List<MediaAdaptation> listar_noticias(){
		return Dclient.listar_noticias();
	}

}