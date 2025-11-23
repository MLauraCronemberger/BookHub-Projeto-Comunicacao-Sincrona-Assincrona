package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
	
	private final AuthorNewsService service;
	
	public QueueConsumer (AuthorNewsService service) {
		this.service = service;
	}
	
    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload AuthorNews nova_adaptacao) {
    	service.salvar_adaptacao(nova_adaptacao);
    	
        System.out.println("Nova Notícia Registrada do Autor: " + nova_adaptacao.getAutor());
    }

}
