package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun delete(){
        bookRepository.deleteAll();
        userRepository.deleteAll();
        userLoanHistoryRepository.deleteAll()
    }
    @Test
    fun save(){
        //given
        val bookRequest = BookRequest("book")

        //when
        bookService.saveBook(bookRequest)

        //then
        assertThat(bookRepository.findAll()).hasSize(1)
        assertThat(bookRepository.findAll()[0].name).isEqualTo("book")
    }

    @Test
    @DisplayName("정상적으로 대여")
    fun loan(){
        //given
        val savedBook = bookRepository.save(Book("book"))
        val savedUser = userRepository.save(User("kdw", 33))
        val bookLoanRequest = BookLoanRequest(savedUser.name, savedBook.name)

        //when
        bookService.loanBook(bookLoanRequest)

        //then
        val results = userLoanHistoryRepository.findAll()

        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("book")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
    }

    @Test
    fun returnBook(){

        //given



        //when


        //then

    }

}