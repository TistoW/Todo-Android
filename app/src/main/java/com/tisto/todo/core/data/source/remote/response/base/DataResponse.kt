package com.tisto.todo.core.data.source.remote.response.base

data class DataResponse<T>(
        var message: String = "",
        var code: String = "",
        var data: T? = null
)