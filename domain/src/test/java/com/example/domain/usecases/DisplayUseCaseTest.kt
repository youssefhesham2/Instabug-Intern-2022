package com.example.domain.usecases

import com.example.domain.entities.HeaderEntity
import org.junit.Assert.*
import org.junit.Test

class DisplayUseCaseTest {
    @Test
    fun `convertParamListToString() with parameters list then return parameters as string()`() {
        //Arrange
        val fakeParamList = listOf(
            HeaderEntity("key", "value"),
            HeaderEntity("key2", "value2")
        )

        //Act
        val result = DisplayUseCase().convertParamListToString(fakeParamList)

        //Assertion
        val expected = "key : value \n" + "key2 : value2 \n"
        assertEquals(expected, result)
    }
}