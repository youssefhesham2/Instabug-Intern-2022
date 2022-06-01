package com.example.data.datasources

import com.example.data.entities.RequestDataEntity
import com.example.data.entities.ResponseRequestDataEntity
import com.example.domain.utils.ResultData

interface RequestApiService {
    fun postRequest(requestDataEntity: RequestDataEntity): ResponseRequestDataEntity

    fun getRequest(requestDataEntity: RequestDataEntity): ResponseRequestDataEntity
}