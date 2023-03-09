package com.group.libraryapp.dto.book.request

import lombok.AllArgsConstructor

data class BookLoanRequest(
    val userName: String,
    val bookName: String
) {

}