package com.example.domain.usecases

import com.example.domain.entities.ResponseEntity

class FilterRequestUsesCase {

    fun filterRequestByType(
        requestType: Int,
        responseEntityList: ArrayList<ResponseEntity>
    ): List<ResponseEntity> {
        return responseEntityList.filter {
            it.requestType == requestType
        }
    }

}