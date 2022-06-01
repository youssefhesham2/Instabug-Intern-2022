package com.example.instabugintern2022.History

import com.example.domain.entities.ResponseEntity
import com.example.instabugintern2022.displayactivity.DisplayView

class HistoryPresenter {
    private var view: HistoryView? = null
    fun onAttached(view: HistoryView) {
        this.view = view
        getBundleData()
    }
    private fun getBundleData(){
        var responseEntityList=view?.getBundleData()
        responseEntityList?.let { view?.notifyDataSetChanged(it) }
    }
}