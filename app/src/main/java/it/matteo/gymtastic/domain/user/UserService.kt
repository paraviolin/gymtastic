package it.matteo.gymtastic.domain.user

import it.matteo.gymtastic.data.user.UserRepository
import it.matteo.gymtastic.data.user.entity.UserEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: UserRepository){
    suspend fun getUserByEmail(email: String): UserEntity {
        return userRepository.getUser(email).first()
    }
 }