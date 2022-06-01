package com.example.data.repositories

import com.example.data.datasources.DatabaseHelper
import com.example.data.datasources.RequestApiService
import com.example.data.datasources.RequestApiServiceImpl
import com.example.data.utils.toDataEntity
import com.example.data.utils.toDomainEntity
import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.repositoryinterfaces.MainRepository
import com.example.domain.utils.ResultData

class MainRepositoryImpl constructor(
    private var requestApiService: RequestApiService = RequestApiServiceImpl(),
    private var databaseHelper: DatabaseHelper
) :
    MainRepository {
    override fun postRequestApi(requestEntity: RequestEntity): ResultData {
        return try {
            val response = requestApiService.postRequest(requestEntity.toDataEntity())

            if (response.responseCode / 100 === 2) ResultData.Successful(response.toDomainEntity())
            else ResultData.Failure(response.toDomainEntity())
        } catch (exception: Exception) {
            ResultData.Exception(exception)
        }
    }

    override fun getRequestApi(requestEntity: RequestEntity): ResultData {
        return try {
            val response = requestApiService.getRequest(requestEntity.toDataEntity())

            if (response.responseCode / 100 === 2) ResultData.Successful(response.toDomainEntity())
            else ResultData.Failure(response.toDomainEntity())
        } catch (exception: Exception) {
            ResultData.Exception(exception)
        }
    }

    override fun cacheData(url: String, responseEntity: ResponseEntity): Boolean {
        return databaseHelper.cache(url, responseEntity.requestType, responseEntity.responseCode)
    }

    override fun getCacheData(): ArrayList<ResponseEntity> {
        val responseEntityList = ArrayList<ResponseEntity>()
        val list = databaseHelper.getCachedData()
        list.forEach {
            responseEntityList.add(it.toDomainEntity())
        }
        return responseEntityList
    }
}