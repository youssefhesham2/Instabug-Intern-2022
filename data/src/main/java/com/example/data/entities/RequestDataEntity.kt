package com.example.data.entities

class RequestDataEntity(
    val url: String,
    val body: String,
    val param: List<HeaderDataEntity>,
    val headers: List<HeaderDataEntity>,
    val networkConnection: Boolean,
    val requestType: Int
)