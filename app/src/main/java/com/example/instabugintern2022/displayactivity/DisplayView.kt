package com.example.instabugintern2022.displayactivity

import com.example.domain.entities.ResponseEntity

interface DisplayView {
    fun getBundleData(): ResponseEntity

    fun setStatusView(status: String)

    fun setRequestHeaderView(requestHeader: String)

    fun setResponseHeaderView(responseHeader: String)

    fun setQueryParamView(queryParam: String)

    fun changeQueryParamViewTitleTo(title:String)

    fun setResponseBodyView(responseBody: String)
}