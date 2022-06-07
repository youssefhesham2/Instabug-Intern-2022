package com.example.instabugintern2022.mainactivity

import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.ResponseEntity

interface MainView {

    fun addNewParamField()

    fun addNewHeaderField()

    fun getUrFromUrlView(): String

    fun getBodyFromBodyView(): String

    fun getUserInternetConnectionState(): Boolean

    fun getParamListFromLayout(): ArrayList<HeaderEntity>

    fun getHeaderListFromLayout(): ArrayList<HeaderEntity>

    fun goneParamViewVisibility()

    fun visibleParamViewVisibility()

    fun goneBodyViewVisibility()

    fun visibleBodyViewVisibility()

    fun loadingDialogShow()

    fun loadingDialogDismiss()

    fun snackbar(message: String, color: Int)

    fun intentToDisplayActivity(responseEntity: ResponseEntity)

    fun intentToHistoryActivity(responseEntityList: ArrayList<ResponseEntity>)
}