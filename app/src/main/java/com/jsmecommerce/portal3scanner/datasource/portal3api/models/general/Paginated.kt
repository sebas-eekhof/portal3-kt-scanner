package com.jsmecommerce.portal3scanner.datasource.portal3api.models.general

data class Paginated<T>(
    val pagination: PaginatedObject,
    val items: List<T>
) {
    data class PaginatedObject(
        val total: Int,
        val page: Int,
        val limit: Int
    )
}
