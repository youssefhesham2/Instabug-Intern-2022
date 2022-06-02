package com.example.instabugintern2022.History

import com.example.domain.entities.ResponseEntity
import com.example.domain.usecases.FilterRequestUsesCase
import com.example.instabugintern2022.displayactivity.DisplayView
import java.text.FieldPosition

class HistoryPresenter constructor(private val filterRequestUsesCase: FilterRequestUsesCase = FilterRequestUsesCase()) {
    private var view: HistoryView? = null
    private var responseEntityList = ArrayList<ResponseEntity>()
    fun onAttached(view: HistoryView) {
        this.view = view
        getBundleData()
    }

    private fun getBundleData() {
        view?.getBundleData()?.let { responseEntityList = it }
        responseEntityList?.let { view?.notifyDataSetChanged(it) }
    }

    fun onSpinnerSelectedItem(position: Int) {
        when (position) {
            0 -> responseEntityList?.let { view?.notifyDataSetChanged(it) }

            1 -> view?.notifyDataSetChanged(
                filterRequestUsesCase.filterRequestByType(
                    0,
                    responseEntityList
                ) as ArrayList<ResponseEntity>
            )

            2 -> view?.notifyDataSetChanged(
                filterRequestUsesCase.filterRequestByType(
                    1,
                    responseEntityList
                ) as ArrayList<ResponseEntity>
            )
        }
    }

    fun onDestroy() {
        view = null
    }
}