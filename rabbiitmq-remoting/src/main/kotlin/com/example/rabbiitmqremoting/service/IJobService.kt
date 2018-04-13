package com.example.rabbiitmqremoting.service

import com.example.rabbiitmqremoting.service.IJobQueryService
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter

interface IJobService : IJobQueryService



