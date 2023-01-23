package org.mogoldb.weberpBackend.dto.response

import org.springframework.data.domain.Page

class PageableDto<T> (
    val size: Int,
    val page: Int,
    val totalPages: Int,
    val results: List<T>,
) {
    companion object {
        fun <T> PageableDto<T>.fromPage(page: Page<T>, results: List<T>) : PageableDto<T> {
            return PageableDto(page.size, page.number, page.totalPages, page.content)
        }
    }
}