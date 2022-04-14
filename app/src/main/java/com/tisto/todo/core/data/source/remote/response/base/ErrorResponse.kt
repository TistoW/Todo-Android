package com.tisto.todo.core.data.source.remote.response.base

data class ErrorResponse(
        val code: Int? = null,
        val message: String? = null,
        val param: String? = null
)