package com.nsz.kotlin.open.source

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import com.nsz.kotlin.ux.common.CommonLog

object HttpServerKernel : HttpServerRequestCallback {

    private const val TAG = "HttpServerKernel"

    private val asyncHttpServer = AsyncHttpServer()

    fun start() {
        asyncHttpServer.listen(9000)
        asyncHttpServer.get("/getTerminalSerialNumber", this)
        asyncHttpServer.post("/postTerminalSerialNumber", this)
    }

    fun stop() {
        asyncHttpServer.stop()
    }

    override fun onRequest(request: AsyncHttpServerRequest ?, response: AsyncHttpServerResponse ? ) {
        Log.e(TAG, "request:$request response:$response")
        if (request != null) {
            val body = request.body
            val path = request.path
            val query = request.query
            val method = request.method
            CommonLog.e("method:$method path:$path")
            CommonLog.e("body:$body")
            CommonLog.e("query:$query")
        }
        if (response != null) {
            response.send("Hello")
        } else {
            CommonLog.e("response is null")
        }
    }

}