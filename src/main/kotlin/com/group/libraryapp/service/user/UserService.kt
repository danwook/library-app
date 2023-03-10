package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun saveUser(request: UserCreateRequest){
        //User에서 null일 경우 default 파라미터가 있기 떄문에
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse>{
        return userRepository.findAll().map {
            UserResponse.of(it)
        }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest){
        val user = userRepository.findByIdOrNull(request.id) ?: fail()
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String){
        val savedUser = userRepository.findByName(name) ?: fail()
        userRepository.delete(savedUser)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories().map {
            user -> UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map {
                    userLoanHistory -> BookHistoryResponse(
                        name = userLoanHistory.bookName,
                        isReturn = userLoanHistory.status == UserLoanStatus.RETURNED
                    )
                }
            )
        }
    }




}