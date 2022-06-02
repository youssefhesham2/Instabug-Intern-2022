package com.example.domain.usecases

import com.example.domain.entities.ResponseEntity
import org.junit.Assert.*
import org.junit.Test

class FilterRequestUsesCaseTest {
    @Test
    fun `filterRequestByType() with GET type then return list of Get requests type`() {
        //arrange
        val responseEntity1 = ResponseEntity(0, 200, "", "", "", "", "", emptyList())
        val responseEntity2 = ResponseEntity(0, 200, "", "", "", "", "", emptyList())
        val responseEntity3 = ResponseEntity(1, 200, "", "", "", "", "", emptyList())
        val responseEntity4 = ResponseEntity(1, 200, "", "", "", "", "", emptyList())
        val responseEntityList = ArrayList<ResponseEntity>()
        responseEntityList.add(responseEntity1)
        responseEntityList.add(responseEntity2)
        responseEntityList.add(responseEntity3)
        responseEntityList.add(responseEntity4)
        val fakeFilterRequestUsesCase = FilterRequestUsesCase()

        //Action
        val result = fakeFilterRequestUsesCase.filterRequestByType(0, responseEntityList)

        //Assertion
        val expected = ArrayList<ResponseEntity>()
        expected.add(responseEntity1)
        expected.add(responseEntity2)

        assertEquals(expected, result)
    }
}