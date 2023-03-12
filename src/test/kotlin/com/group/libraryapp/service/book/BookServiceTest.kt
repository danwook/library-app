package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
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
        bookRepository.deleteAll()
        userRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }
    @Test
    fun save(){
        //given
        val bookRequest = BookRequest("book", BookType.COMPUTER)

        //when
        bookService.saveBook(bookRequest)

        //then
        assertThat(bookRepository.findAll()).hasSize(1)
        assertThat(bookRepository.findAll()[0].name).isEqualTo("book")
        assertThat(bookRepository.findAll()[0].type).isEqualTo(BookType.COMPUTER)
    }

    @Test
    @DisplayName("정상적으로 대여")
    fun loan(){
        //given
        val savedBook = bookRepository.save(Book.fixture("book"))
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

    @Test
    @DisplayName("책 대여 권수를 정상 확인한다")
    fun countLoanBookTest(){
        //given
        val savedUser = userRepository.save(User("kdw", 33))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "A"),
            UserLoanHistory.fixture(savedUser, "B", UserLoanStatus.RETURNED),
            UserLoanHistory.fixture(savedUser, "C", UserLoanStatus.RETURNED)
        ))

        //when
        val result = bookService.countLoanedBook();

        //then
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("분야별 책 권수를 정상 확인한다")
    fun getBookStaticsTest(){

        //given
        bookRepository.saveAll(listOf(
            Book.fixture("A"),
            Book.fixture("D"),
            Book.fixture("B", BookType.ECONOMY),
            Book.fixture("C", BookType.SCIENCE)
        ))

        //when
        val result = bookService.getBookStatics()

        //then
        assertThat(result[0].count).isEqualTo(2)

    }

}