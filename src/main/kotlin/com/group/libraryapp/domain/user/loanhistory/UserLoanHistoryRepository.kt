package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {
    fun findByBookNameAndIsReturn(bookName: String, isReturn: Boolean): UserLoanHistory?
    // TODO: 옵셔널처리 안하면 null이 될수도 있어서 ? 붙여줘야함 
}