package com.example.instabugintern2022.mainactivity

import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.usecases.MainUseCase
import com.example.domain.utils.ResultData
import com.example.instabugintern2022.R
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainPresenter constructor(
    private var mainUseCase: MainUseCase?,
    private var executor: ExecutorService? = Executors.newSingleThreadExecutor()
) {
    private var position: Int = 0
    private var view: MainView? = null
    private var url: String = ""
    fun onAttached(view: MainView) {
        this.view = view
    }

    fun onClickAddNewParamBtn() {
        view?.addNewParamField()
    }

    fun onClickAddNewHeaderBtn() {
        view?.addNewHeaderField()
    }

    fun onClickSendRequestBtn() {
        view?.progressShow()
        val internetConnection = view?.getUserInternetConnectionState() ?: false
        val url = view?.getUrFromUrlView() ?: ""
        val headers = view?.getHeaderListFromLayout() ?: ArrayList<HeaderEntity>()
        val param = view?.getParamListFromLayout() ?: ArrayList<HeaderEntity>()
        val body = view?.getBodyFromBodyView() ?: ""
        val requestEntity =
            RequestEntity(url, body, param, headers, internetConnection, position)

        if (position == 0) sendGetRequest(requestEntity)
        else sendPostRequest(requestEntity)
    }

    private fun sendPostRequest(requestDomainEntity: RequestEntity) {
        url = requestDomainEntity.url
        val response =
            executor?.submit(Callable { mainUseCase?.sendPostRequest(requestDomainEntity) })
                ?.get()
        when (response) {
            is ResultData.Successful<*> -> onSuccessRequest(response.result as ResponseEntity)

            is ResultData.InvalidData -> onInvalidData(response.message)

            is ResultData.Failure<*> -> onFailureRequest(response.result as ResponseEntity)

            is ResultData.Exception -> onException(response.exception)
        }
    }

    private fun sendGetRequest(requestDomainEntity: RequestEntity) {
        url = requestDomainEntity.url
        val response =
            executor?.submit(Callable { mainUseCase?.sendGetRequest(requestDomainEntity) })
                ?.get()
        when (response) {
            is ResultData.Successful<*> -> onSuccessRequest(response.result as ResponseEntity)

            is ResultData.InvalidData -> onInvalidData(response.message)

            is ResultData.Failure<*> -> onFailureRequest(response.result as ResponseEntity)

            is ResultData.Exception -> onException(response.exception)
        }
    }

    fun onSpinnerSelectedItem(position: Int) {
        this.position = position
        if (position == 0) {
            view?.goneBodyViewVisibility()
            view?.visibleParamViewVisibility()
        } else {
            view?.visibleBodyViewVisibility()
            view?.goneParamViewVisibility()
        }
    }

    fun onClickHistoryBtn() {
        val cacheList = mainUseCase?.getCacheData()
        cacheList?.let { view?.intentToHistoryActivity(it) }
    }

    private fun onSuccessRequest(responseEntity: ResponseEntity) {
        mainUseCase?.cacheData(url, responseEntity)
        view?.progressDismiss()
        view?.intentToDisplayActivity(responseEntity)
    }

    private fun onInvalidData(message: String) {
        view?.progressDismiss()
        view?.snackbar(message, R.color.wrong)
    }

    private fun onFailureRequest(responseEntity: ResponseEntity) {
        mainUseCase?.cacheData(url, responseEntity)
        view?.progressDismiss()
        view?.intentToDisplayActivity(responseEntity)
    }

    private fun onException(exception: Throwable) {
        view?.progressDismiss()
        view?.snackbar(exception.localizedMessage, R.color.wrong)
    }

    fun onDestroy() {
        view = null
        executor = null
        mainUseCase = null
    }
}