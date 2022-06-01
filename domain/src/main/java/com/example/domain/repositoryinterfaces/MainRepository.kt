package com.example.domain.repositoryinterfaces

import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.utils.ResultData

interface MainRepository {
    fun postRequestApi(requestEntity: RequestEntity): ResultData
    fun getRequestApi(requestEntity: RequestEntity): ResultData
    fun cacheData(url: String, responseEntity: ResponseEntity): Boolean {
        return false
    }

    fun getCacheData(): ArrayList<ResponseEntity> {
        return emptyList<ResponseEntity>() as ArrayList<ResponseEntity>
    }
}