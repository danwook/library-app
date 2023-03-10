package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun delete() {
        userRepository.deleteAll()
    }

    @Test
    fun userSaveTest() {
        //given
        val request = UserCreateRequest("김단우", null)

        //when
        userService.saveUser(request)

        //then
        val results = userRepository.findAll()

        assertThat(results[0].name).isEqualTo("김단우")
        assertThat(results[0].age).isNull() //Exception?
    }

    @Test
    fun getUsersTest() {
        //given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        //when
        val users = userService.getUsers()

        //then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, null)

    }

    @Test
    fun updateUserNameTest() {
        //given
        val savedUser = userRepository.save(User("kdw", 33))
//        val userUpdateRequest = savedUser.id?.let { UserUpdateRequest(it, "kdanwoo") }
        val userUpdateRequest = UserUpdateRequest(savedUser.id!!, "kdanwoo")

        //when
        userService.updateUserName(userUpdateRequest)

        //then
        val updatedUser = userRepository.findById(savedUser.id!!)
        assertThat(updatedUser.get().name).isEqualTo("kdanwoo")

    }

    @Test
    fun deleteUser() {
        val savedUser = userRepository.save(User("kdw", 33))

        userService.deleteUser("kdw")

        assertThat(userRepository.findAll()).hasSize(0)
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    fun getUserLoanHistoriesTest1(){
        //given
        userRepository.save(User("A", null))

        //when
        val results = userService.getUserLoanHistories()

        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()

    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다")
    fun getUserLoanHistoriesTest2() {
        // given
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

}