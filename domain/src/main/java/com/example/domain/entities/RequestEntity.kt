package com.example.domain.entities

class RequestEntity(
    var url: String,
    var body: String,
    var param: List<HeaderEntity>,
    var headers: List<HeaderEntity>,
    var networkConnection: Boolean,
    var requestType: Int
)