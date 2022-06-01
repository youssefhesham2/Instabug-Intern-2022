package com.example.instabugintern2022.History

import com.example.domain.entities.ResponseEntity

interface HistoryView {
    fun getBundleData(): ArrayList<ResponseEntity>

    fun notifyDataSetChanged(responseEntityList:ArrayList<ResponseEntity>)
}