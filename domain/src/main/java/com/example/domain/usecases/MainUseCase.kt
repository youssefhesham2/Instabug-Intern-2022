package com.example.domain.usecases

import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.repositoryinterfaces.MainRepository
import com.example.domain.utils.ResultData

class MainUseCase(private var mainRepository: MainRepository) {

    fun sendPostRequest(requestEntity: RequestEntity): ResultData {
        requestEntity.headers = filterEmptyHeaders(requestEntity.headers)
        requestEntity.param = filterEmptyParam(requestEntity.param)

        return if (!requestEntity.networkConnection) ResultData.InvalidData("Sorry,No Internet Connection Please Check You Internet and try again!")
        else if (requestEntity.url.isEmpty()) ResultData.InvalidData("Please Enter The URL")
        else mainRepository.postRequestApi(requestEntity)
    }

    fun sendGetRequest(requestEntity: RequestEntity): ResultData {
        requestEntity.headers = filterEmptyHeaders(requestEntity.headers)
        requestEntity.param = filterEmptyParam(requestEntity.param)
        requestEntity.url = addParamToUrl(requestEntity.url, requestEntity.param)

        return if (!requestEntity.networkConnection) ResultData.InvalidData("Sorry,No Internet Connection Please Check You Internet and try again!")
        else if (requestEntity.url.isEmpty()) ResultData.InvalidData("Please Enter The URL")
        else mainRepository.getRequestApi(requestEntity)
    }

    fun cacheData(url: String, responseEntity: ResponseEntity): Boolean {
        return mainRepository.cacheData(url, responseEntity)
    }

    fun getCacheData(): ArrayList<ResponseEntity> {
        return mainRepository.getCacheData()
    }

    fun filterEmptyHeaders(headers: List<HeaderEntity>): List<HeaderEntity> {
        return headers.filter {
            it.key.isNotEmpty()
            it.value.isNotEmpty()
        }
    }

    fun filterEmptyParam(param: List<HeaderEntity>): List<HeaderEntity> {
        return param.filter {
            it.key.isNotEmpty()
            it.value.isNotEmpty()
        }
    }

    fun addParamToUrl(url: String, params: List<HeaderEntity>): String {
        if (params.isEmpty()) return url

        var urlWithParam = "$url?"
        params.forEach {
            urlWithParam += "${it.key}=${it.value}&"
        }
        return urlWithParam
    }
}