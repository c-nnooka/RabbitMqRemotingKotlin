package com.example.rabbiitmqremoting.controller

import com.example.rabbiitmqremoting.args.job.QueryJobArgs
import com.example.rabbiitmqremoting.response.QueryJobResponse
import com.example.rabbiitmqremoting.service.IJobQueryService
import com.example.rabbiitmqremoting.service.IJobService


class JobController(val jobQueryService: IJobQueryService) : IJobService {


    override fun findJobById(args: QueryJobArgs): QueryJobResponse {
        return jobQueryService.findJobById(args)
    }


}