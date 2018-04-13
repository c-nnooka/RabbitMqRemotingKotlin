package com.example.rabbiitmqremoting

import com.example.rabbiitmqremoting.args.job.QueryJobArgs
import com.example.rabbiitmqremoting.models.Job
import com.example.rabbiitmqremoting.service.IJobService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class RabbitCmdRunner: CommandLineRunner{

    @Autowired
    lateinit var applicationContext: ApplicationContext

    override fun run(vararg args: String?) {
        val jobservice  = applicationContext.getBean(IJobService::class.java)

        println("Remoting Is On => " +  jobservice.findJobById(QueryJobArgs().apply { job = Job().apply { id = 1 } } ))
    }
}