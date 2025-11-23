package com.example.demo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class SafeSimpleMessageConverter extends SimpleMessageConverter {

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        MessageProperties props = message.getMessageProperties();
        if (props != null) {
            // content_type ausente → assume texto puro
            if (props.getContentType() == null) {
                props.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
            }
        }
        return super.fromMessage(message);
    }
}
