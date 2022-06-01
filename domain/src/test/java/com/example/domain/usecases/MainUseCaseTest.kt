package com.example.domain.usecases

import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.RequestEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.repositoryinterfaces.MainRepository
import com.example.domain.utils.ResultData
import org.junit.Assert.*
import org.junit.Test

class MainUseCaseTest {

    private val fakeMainRepository = object : MainRepository {
        override fun postRequestApi(requestEntity: RequestEntity): ResultData {
            return ResultData.InvalidData("")
        }

        override fun getRequestApi(requestEntity: RequestEntity): ResultData {
            return ResultData.InvalidData("")
        }

    }

    @Test
    fun `sendGetRequest() with query parameters then return ResultDataSuccessful()`() {
        //Arrange
        val fakeResponse = ResponseEntity(0, 200, "", "", "", "", "", emptyList())
        val fakeParam = listOf(HeaderEntity("param_key", "param_value"))
        val fakeRequestEntity = RequestEntity("www.url.com", "0", fakeParam, emptyList(), true, 0)
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.InvalidData("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                return ResultData.Successful(fakeResponse)
            }

        }

        //Act
        val result = MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        val expected = ResultData.Successful(fakeResponse)
        assertEquals(expected, result)
    }

    @Test
    fun `Failed sendGetRequest() then return ResultDataFailure() with 500 error code`() {
        //Arrange
        val fakeResponse = ResponseEntity(0, 500, "", "", "", "", "", emptyList())
        val fakeRequestEntity = RequestEntity("www.url.com", "0", emptyList(), emptyList(), true, 0)
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                // not testing
                return ResultData.Failure(fakeResponse)
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //testing
                return ResultData.Failure(fakeResponse)
            }

        }

        //Act
        val response = MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)
        var result =0
        when (response) {
            is ResultData.Failure<*> -> result = (response.result as ResponseEntity).responseCode
        }


        //Assertion
        val expected = 500
        assertEquals(expected, result)
    }

    @Test
    fun `sendPostRequest() then return ResultDataSuccessful()`() {
        //Arrange
        val fakeResponse = ResponseEntity(1, 201, "", "", "", "", "", emptyList())
        val fakeRequestEntity = RequestEntity("www.url.com", "0", emptyList(), emptyList(), true, 0)
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //testing
                return ResultData.Successful(fakeResponse)
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Failure("")
            }

        }

        //Act
        val result = MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertion
        val expected = ResultData.Successful(fakeResponse)
        assertEquals(expected, result)
    }

    @Test
    fun `Failed sendPostRequest() then return ResultDataFailure() with 404 error code`() {
        //Arrange
        val fakeResponse = ResponseEntity(0, 404, "", "", "", "", "", emptyList())
        val fakeRequestEntity = RequestEntity("www.url.com", "0", emptyList(), emptyList(), true, 0)
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                // testing
                return ResultData.Failure(fakeResponse)
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Successful("")
            }

        }

        //Act
        val response = MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)
        var result = 0
        when (response) {
            is ResultData.Failure<*> -> result = (response.result as ResponseEntity).responseCode
        }


        //Assertion
        val expected = 404
        assertEquals(expected, result)
    }

    //
    @Test
    fun `sendPostRequest() with device is offline then return ResultInvalidData(no internet msg)`() {
        //Arrange
        val fakeRequestEntity =
            RequestEntity("www.url.com", "0", emptyList(), emptyList(), false, 0)
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing testing
                return ResultData.Failure("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Failure("")
            }

        }

        //Act
        val result = MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertion
        val expected =
            ResultData.InvalidData("Sorry,No Internet Connection Please Check You Internet and try again!")
        assertEquals(expected, result)
    }

    @Test
    fun `sendPostRequest() with empty url then return ResultInvalidData(no url msg)`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("", "0", emptyList(), emptyList(), true, 0)

        //Act
        val result = MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertion
        val expected = ResultData.InvalidData("Please Enter The URL")
        assertEquals(expected, result)
    }

    @Test
    fun `sendPostRequest() with device is offline then not invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("ww.url.com", "0", emptyList(), emptyList(), false, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                invoke = true
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertion
        assertFalse(invoke)
    }

    @Test
    fun `sendPostRequest() with empty url then not invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("", "0", emptyList(), emptyList(), true, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                invoke = true
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertion
        assertFalse(invoke)
    }

    @Test
    fun `sendPostRequest() with url and online connection then invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("www.url.com", "0", emptyList(), emptyList(), true, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                invoke = true
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendPostRequest(fakeRequestEntity)

        //Assertio
        assertTrue(invoke)
    }

    //get requests
    @Test
    fun `sendGetRequest() with device is offline then return ResultInvalidData(no internet msg)`() {
        //Arrange
        val fakeRequestEntity =
            RequestEntity("www.url.com", "0", emptyList(), emptyList(), false, 0)

        //Act
        val result = MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        val expected =
            ResultData.InvalidData("Sorry,No Internet Connection Please Check You Internet and try again!")
        assertEquals(expected, result)
    }

    @Test
    fun `sendGetRequest() with empty url then return ResultInvalidData(no url msg)`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("", "0", emptyList(), emptyList(), true, 0)

        //Act
        val result = MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        val expected = ResultData.InvalidData("Please Enter The URL")
        assertEquals(expected, result)
    }

    @Test
    fun `sendGetRequest() with device is offline then not invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("ww.url.com", "0", emptyList(), emptyList(), false, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //testing invoking
                invoke = true
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        assertFalse(invoke)
    }

    @Test
    fun `sendGetRequest() with empty url then not invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("", "0", emptyList(), emptyList(), true, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //invoke
                invoke = true
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        assertFalse(invoke)
    }

    @Test
    fun `sendGetRequest() with url and online connection then invoke postRequestApi`() {
        //Arrange
        val fakeRequestEntity = RequestEntity("www.url.com", "0", emptyList(), emptyList(), true, 0)
        var invoke = false
        val fakeMainRepository = object : MainRepository {
            override fun postRequestApi(requestEntity: RequestEntity): ResultData {
                //not testing
                return ResultData.Successful("")
            }

            override fun getRequestApi(requestEntity: RequestEntity): ResultData {
                //invoke
                invoke = true
                return ResultData.Failure("")
            }

        }

        //Act
        MainUseCase(fakeMainRepository).sendGetRequest(fakeRequestEntity)

        //Assertion
        assertTrue(invoke)
    }

    // filters
    @Test
    fun `filterEmptyHeaders() with headers list have empty values then return headers list without empty value`() {
        //Arrange
        val fakeHeaderList = listOf(
            HeaderEntity("key", "value"),
            HeaderEntity("", ""),
            HeaderEntity("key2", "value2"),
            HeaderEntity("", ""),
            HeaderEntity("", "")
        )

        val expected =
            listOf(HeaderEntity("key", "value"), HeaderEntity("key2", "value2"))

        //Act
        val result = MainUseCase(fakeMainRepository).filterEmptyHeaders(fakeHeaderList)

        //Assertion
        assertEquals(expected, result)
    }

    @Test
    fun `filterEmptyParam() with param list have empty values then return param list without empty value`() {
        //Arrange
        val fakeParamList = listOf(
            HeaderEntity("key", "value"),
            HeaderEntity("", ""),
            HeaderEntity("key2", "value2"),
            HeaderEntity("", ""),
            HeaderEntity("", "")
        )

        val expected =
            listOf(HeaderEntity("key", "value"), HeaderEntity("key2", "value2"))

        //Act
        val result = MainUseCase(fakeMainRepository).filterEmptyHeaders(fakeParamList)

        //Assertion
        assertEquals(expected, result)
    }

    @Test
    fun `addParamToUrl() with base url and params list then return url with params`() {
        //Arrange
        val fakeParamList = listOf(
            HeaderEntity("key", "value"),
            HeaderEntity("key2", "value2")
        )
        val baseUrl = "www.url.com"

        //Act
        val result = MainUseCase(fakeMainRepository).addParamToUrl(baseUrl, fakeParamList)

        //Assertion
        val expected = "www.url.com?key=value&key2=value2&"

        assertEquals(expected, result)
    }
}