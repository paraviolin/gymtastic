package it.matteo.gymtastic.domain.user

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.user.UserRepository
import it.matteo.gymtastic.data.user.entity.UserEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: UserRepository){
    suspend fun getUserByEmail(email: String): UserEntity {
        return userRepository.getUser(email).first()
    }

     fun updateUser(user: UserEntity)  {
        userRepository.updateUser(user)
    }
 }