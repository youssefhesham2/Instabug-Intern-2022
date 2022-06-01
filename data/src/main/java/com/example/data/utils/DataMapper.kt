package com.example.data.utils

import com.example.data.entities.HeaderDataEntity
import com.example.data.entities.RequestDataEntity
import com.example.data.entities.ResponseRequestDataEntity
import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity

fun HeaderEntity.toDataEntity() = HeaderDataEntity(key, value)
fun HeaderDataEntity.toDomainEntity() = HeaderEntity(key, value)

fun RequestEntity.toDataEntity() =
    RequestDataEntity(
        url,
        body,
        toDataParamList(),
        toDataHeadersList(),
        networkConnection,
        requestType
    )

fun RequestDataEntity.toDomainEntity() =
    RequestEntity(
        url,
        body,
        toDomainParamList(),
        toDomainHeaderList(),
        networkConnection,
        requestType
    )


fun RequestEntity.toDataHeadersList() = headers.map {
    HeaderDataEntity(it.key, it.value)
}

fun RequestDataEntity.toDomainHeaderList() = headers.map {
    HeaderEntity(it.key, it.value)
}

fun RequestEntity.toDataParamList() = param.map {
    HeaderDataEntity(it.key, it.value)
}

fun RequestDataEntity.toDomainParamList() = param.map {
    HeaderEntity(it.key, it.value)
}

fun ResponseRequestDataEntity.toDomainEntity() = ResponseEntity(
    requestType,
    responseCode,
    status,
    responseBody,
    requestHeaders,
    responseHeaders,
    requestBody,
    toDomainParamList()
)

fun ResponseEntity.toDataEntity() = ResponseRequestDataEntity(
    requestType,
    responseCode,
    status,
    responseBody,
    requestHeaders,
    responseHeaders,
    requestBody,
    toDataParamList()
)

fun ResponseEntity.toDataParamList() = requestParam.map {
    HeaderDataEntity(it.key, it.value)
}

fun ResponseRequestDataEntity.toDomainParamList() = requestParam.map {
    HeaderEntity(it.key, it.value)
}

