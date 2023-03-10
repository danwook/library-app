package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?
    // TODO: 옵셔널처리 안하면 null이 될수도 있어서 ? 붙여줘야함
}