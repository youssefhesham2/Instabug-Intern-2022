package com.example.data.datasources

import com.example.data.entities.HeaderDataEntity
import com.example.data.entities.RequestDataEntity
import com.example.data.entities.ResponseRequestDataEntity
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class RequestApiServiceImpl : RequestApiService {

    override fun postRequest(requestDataEntity: RequestDataEntity): ResponseRequestDataEntity {
        var requestHeaders = ""
        return try {
            val url = URL(requestDataEntity.url)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8")
            connection.setRequestProperty("Accept", "application/json")
            requestHeaders += "Accept: application/json \n"
            requestDataEntity.headers.forEach {
                connection.setRequestProperty(it.key, it.value)
                requestHeaders += "${it.key}: ${it.value} \n"
            }
            connection.setReadTimeout(10 * 1000)
            connection.setUseCaches(true)
            connection.setDoInput(true)
            connection.setDoOutput(true)

            //Request body
            val wr = DataOutputStream(connection.getOutputStream())
            wr.writeBytes(requestDataEntity.body)
            wr.flush()
            wr.close()

            //Response
            val rd =
                BufferedReader(InputStreamReader(if (connection.getResponseCode() / 100 === 2) connection.getInputStream() else connection.getErrorStream()))

            var line: String?
            val responseBody = StringBuffer()
            //Expecting answer of type JSON single line {"json_items":[{"status":"OK","message":"<Message>"}]}
            while (rd.readLine().also { line = it } != null) {
                responseBody.append(line)
            }
            rd.close()

            //Response Header
            val map: Map<String, List<String>> = connection.getHeaderFields()
            val responseHeaders = StringBuffer()
            for (entry in map.entries) {
                responseHeaders.appendLine("${entry.key} : ${entry.value}")
            }
            val responseCode = connection.responseCode
            val responseMsg = connection.responseMessage
            connection.disconnect() // close the connection after usage

            ResponseRequestDataEntity(
                1,
                responseCode,
                responseMsg,
                responseBody.toString(),
                requestHeaders,
                responseHeaders.toString(),
                requestDataEntity.body,
                requestDataEntity.param
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getRequest(requestDataEntity: RequestDataEntity): ResponseRequestDataEntity {
        //Request body
        var requestHeaders = ""
        val url: URL
        var connection: HttpURLConnection? = null
        return try {
            url = URL(requestDataEntity.url)
            connection = url
                .openConnection() as HttpURLConnection
            //headers
            connection.setRequestProperty("Accept", "application/json")
            requestHeaders += "Accept: application/json \n"
            requestDataEntity.headers.forEach {
                connection.setRequestProperty(it.key, it.value)
                requestHeaders += "${it.key}: ${it.value} \n"
            }
            connection.setReadTimeout(10 * 1000)
            //Response
            val rd =
                BufferedReader(InputStreamReader(if (connection.getResponseCode() / 100 == 2) connection.getInputStream() else connection.getErrorStream()))

            var line: String?
            val responseBody = StringBuffer()
            //Expecting answer of type JSON single line {"json_items":[{"status":"OK","message":"<Message>"}]}
            while (rd.readLine().also { line = it } != null) {
                responseBody.append(line)
            }
            rd.close()
            //Response Header
            val map: Map<String, List<String>> = connection.getHeaderFields()
            val responseHeaders = StringBuffer()
            for (entry in map.entries) {
                responseHeaders.appendLine("${entry.key} : ${entry.value}")
            }
            connection.disconnect()
            ResponseRequestDataEntity(
                0,
                connection.responseCode,
                connection.responseMessage,
                responseBody.toString(),
                requestHeaders,
                responseHeaders.toString(),
                "",
                requestDataEntity.param
            )
        } catch (e: java.lang.Exception) {
            throw e
        } finally {
            connection?.disconnect()
        }
    }
}