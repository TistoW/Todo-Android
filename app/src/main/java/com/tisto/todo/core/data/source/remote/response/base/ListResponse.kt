package com.tisto.todo.core.data.source.remote.response.base

data class ListResponse<T>(
        var message: String = "",
        var code: String = "",
        val current_page: Int = 0,
        val first_page_url: String? = null,
        val from: Int? = null,
        val last_page: Int = 0,
        val last_page_url: String? = null,
        val next_page_url: String? = null,
        val path: String? = null,
        val per_page: Int? = null,
        val prev_page_url: String? = null,
        val to: Int? = null,
        val total: Int? = null,
        var data: List<T> = arrayListOf()
)