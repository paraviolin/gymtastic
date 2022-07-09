package it.matteo.gymtastic.domain.user

import it.matteo.gymtastic.data.user.UserRepository
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.domain.utils.UserConverter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: UserRepository){
    suspend fun getUserByEmail(email: String): UserModel {
        return UserConverter.toModel(userRepository.getUser(email).first())
    }

    suspend fun getUserById(id: String): UserModel {
        return UserConverter.toModel(userRepository.getUserById(id).first())
    }

     fun updateUser(user: UserModel)  {
        userRepository.updateUser(UserConverter.toEntity(user))
    }

    suspend fun getCustomers(): List<UserModel> {
        return userRepository.getCustomers().first().map {
            UserConverter.toModel(it)
        }
    }
 }