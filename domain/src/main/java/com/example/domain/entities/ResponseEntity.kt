package com.example.domain.entities

import java.io.Serializable

class ResponseEntity(
    val requestType: Int,
    val responseCode: Int,
    val status: String,
    val responseBody: String,
    val requestHeaders: String,
    val responseHeaders: String,
    val requestBody: String,
    val requestParam: List<HeaderEntity>,
):Serializable