package ru.kpfu.itis.pdfgenerator.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.pdfgenerator.utils.PdfType;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${rabbit.exchange}")
    private String exchange;

    @Value("${rabbit.queue.address}")
    private String queueAddress;

    @Value("${rabbit.queue.bank-account}")
    private String queueBankAccount;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(port);
        connectionFactory.setHost(host);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }

    @Bean
    public Queue addressQueue() {
        return new Queue(queueAddress);
    }

    @Bean
    public Queue bankAccountQueue() {
        return new Queue(queueBankAccount);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding addressBinding(){
        return BindingBuilder.bind(addressQueue()).to(directExchange()).with(PdfType.ADDRESS.name());
    }

    @Bean
    public Binding bankAccountBinding(){
        return BindingBuilder.bind(bankAccountQueue()).to(directExchange()).with(PdfType.BANK_ACCOUNT.name());
    }


}
