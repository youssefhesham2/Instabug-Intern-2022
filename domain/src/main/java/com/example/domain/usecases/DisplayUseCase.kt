package com.example.domain.usecases

import com.example.domain.entities.HeaderEntity

class DisplayUseCase {
    fun convertParamListToString(params: List<HeaderEntity>): String {
        var stringParam: String = ""
        params.forEach { stringParam += "${it.key} : ${it.value} \n" }
        return stringParam
    }
}