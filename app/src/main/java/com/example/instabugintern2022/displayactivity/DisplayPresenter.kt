package com.example.instabugintern2022.displayactivity

import com.example.domain.entities.ResponseEntity
import com.example.domain.usecases.DisplayUseCase

class DisplayPresenter constructor(private var displayUseCase: DisplayUseCase = DisplayUseCase()) {
    private var view: DisplayView? = null
    private var responseEntity: ResponseEntity? = null
    fun onAttached(view: DisplayView) {
        this.view = view
        getBundleData()
    }

    private fun getBundleData() {
        responseEntity = view?.getBundleData()

        val requestType = responseEntity?.requestType
        val status = "${responseEntity?.responseCode} ${responseEntity?.status}"
        val requestHeaders = "${responseEntity?.requestHeaders}"
        val responseHeaders = "${responseEntity?.responseHeaders}"
        val requestBody = "${responseEntity?.requestBody}"
        val responseBody = "${responseEntity?.responseBody}"
        val params =
            responseEntity?.requestParam?.let { displayUseCase.convertParamListToString(it) }

        view?.setStatusView(status)
        view?.setRequestHeaderView(requestHeaders)
        view?.setResponseHeaderView(responseHeaders)
        view?.setResponseBodyView(responseBody)
        if (requestType == 1) {
            view?.changeQueryParamViewTitleTo("Request Body")
            view?.setQueryParamView(requestBody)
        } else {
            view?.changeQueryParamViewTitleTo("Query Parameters")
            view?.setQueryParamView(params.toString())
        }
    }
}