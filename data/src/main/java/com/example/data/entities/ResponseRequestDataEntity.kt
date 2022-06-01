package com.example.data.entities

class ResponseRequestDataEntity(
    val requestType: Int,
    val responseCode: Int,
    val status: String,
    val responseBody: String,
    val requestHeaders: String,
    val responseHeaders: String,
    val requestBody: String,
    val requestParam: List<HeaderDataEntity>,
)