package com.example.OrderCartService.configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfiguration {
    public static final String ROUTING_ORDER_EMAIL ="routing.OrderEmail";
    public static final String ROUTING_MERCHANT_ORDER ="routing.MerchantOrder";
    public static final String ROUTING_ORDER_PRODUCT ="routing.OrderProduct";

    @Bean
    Queue queueOrderEmail(){
        return new Queue("queue.OrderEmail",false);
    }

    @Bean
    Queue queueMerchantOrder(){
        return new Queue("queue.MerchantOrder",false);
    }

    @Bean
    Queue queueOrderProduct(){
        return new Queue("queue.OrderProduct",false);
    }






    @Bean
    DirectExchange exchange(){
        return new DirectExchange("direct.exchange");
    }

    @Bean
    Binding bindingOrderEmail(Queue queueOrderEmail, DirectExchange exchange){
        return BindingBuilder.bind(queueOrderEmail).to(exchange).with(ROUTING_ORDER_EMAIL);
    }

    @Bean
    Binding bindingMerchantOrder(Queue queueMerchantOrder, DirectExchange exchange){
        return BindingBuilder.bind(queueMerchantOrder).to(exchange).with(ROUTING_MERCHANT_ORDER);
    }

    @Bean
    Binding bindingOrderProduct(Queue queueOrderProduct, DirectExchange exchange){
        return BindingBuilder.bind(queueOrderProduct).to(exchange).with(ROUTING_ORDER_PRODUCT);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}