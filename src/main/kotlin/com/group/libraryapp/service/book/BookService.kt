package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @Transactional
    fun saveBook(request: BookRequest) {
        bookRepository.save(Book(request.name, request.type))
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val savedBook = bookRepository.findByName(request.bookName) ?: fail()
        if (userLoanHistoryRepository.findByBookNameAndIsReturn(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("대출되어 있음")
        }

        val savedUser = userRepository.findByName(request.userName) ?: fail()
        savedUser.loanBook(savedBook)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val savedUser = userRepository.findByName(request.userName) ?: fail()
        savedUser.returnBook(request.bookName)
    }

}