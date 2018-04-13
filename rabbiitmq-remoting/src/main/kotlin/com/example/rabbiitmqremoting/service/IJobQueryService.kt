package com.example.rabbiitmqremoting.service

import com.example.rabbiitmqremoting.args.job.QueryJobArgs
import com.example.rabbiitmqremoting.response.QueryJobResponse


interface IJobQueryService{

    fun findJobById(args : QueryJobArgs) : QueryJobResponse

}