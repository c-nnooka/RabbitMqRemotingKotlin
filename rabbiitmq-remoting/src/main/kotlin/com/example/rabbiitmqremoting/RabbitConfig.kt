package com.example.rabbiitmqremoting

import com.example.rabbiitmqremoting.controller.JobController
import com.example.rabbiitmqremoting.provider.JobQueryProvider
import com.example.rabbiitmqremoting.service.IJobService
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory


@Configuration
class RabbitConfig : RabbitListenerConfigurer {


    override fun configureRabbitListeners(registrar: RabbitListenerEndpointRegistrar) {
        registrar.messageHandlerMethodFactory = messageHandlerMethodFactory();
    }


    @Bean
    fun consumerJackson2MessageConverter(): MappingJackson2MessageConverter {
        return MappingJackson2MessageConverter()
    }

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter();
    }

    @Bean
    fun messageHandlerMethodFactory(): DefaultMessageHandlerMethodFactory {
        val factory = DefaultMessageHandlerMethodFactory()
        factory.setMessageConverter(consumerJackson2MessageConverter())


        return factory
    }

    @Bean
    fun rmsExporter(rabbitTemplate: RabbitTemplate) : AmqpInvokerServiceExporter {
        val exporter = AmqpInvokerServiceExporter();
        exporter.amqpTemplate = rabbitTemplate;
        exporter.service = JobController(JobQueryProvider());
        exporter.serviceInterface = IJobService::class.java
        exporter.messageConverter = producerJackson2MessageConverter()
        return exporter;
    }

    @Bean
    fun rmxProxy(rabbitTemplate: RabbitTemplate) : AmqpProxyFactoryBean {
        val proxy = AmqpProxyFactoryBean();
        proxy.amqpTemplate = rabbitTemplate;
        proxy.serviceInterface = IJobService::class.java
        proxy.routingKey = "rms.webservice.api"
        return proxy;
    }

    @Bean
    fun registerReplyListener(connectionFactory: CachingConnectionFactory, rabbitTemplate: RabbitTemplate): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames("rms.apiCall")
        container.setMessageListener(rmsExporter(rabbitTemplate))
        container.messageConverter = producerJackson2MessageConverter()
        container.setReceiveTimeout(600000)


        return container
    }

}
