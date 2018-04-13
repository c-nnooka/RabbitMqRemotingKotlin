package com.example.rabbiitmqremoting.provider

import com.example.rabbiitmqremoting.service.IJobQueryService
import com.example.rabbiitmqremoting.args.job.QueryJobArgs
import com.example.rabbiitmqremoting.response.QueryJobResponse

class JobQueryProvider : IJobQueryService {
    override fun findJobById(args: QueryJobArgs): QueryJobResponse {

        println("Hellow From RMS")

        return QueryJobResponse().apply {response = (args.job!!.id?.plus(1))}
    }
}