package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun delete(){
        userRepository.deleteAll()
    }

    @Test
    fun userSaveTest(){
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
    fun getUsersTest(){
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
        assertThat(users).extracting("name").containsExactlyInAnyOrder("A","B")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, null)

    }

    @Test
    fun updateUserNameTest(){
        //given
        val savedUser = userRepository.save(User("kdw", 33))
        val userUpdateRequest = UserUpdateRequest(savedUser.id, "kdanwoo")

        //when
        userService.updateUserName(userUpdateRequest)

        //then
        val updatedUser = userRepository.findById(savedUser.id)
        assertThat(updatedUser.get().name).isEqualTo("kdanwoo")
    }

    @Test
    fun deleteUser(){
        val savedUser = userRepository.save(User("kdw", 33))

        userService.deleteUser("kdw")

        assertThat(userRepository.findAll()).hasSize(0)
    }

}