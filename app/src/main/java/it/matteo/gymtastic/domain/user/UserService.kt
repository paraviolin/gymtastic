package it.matteo.gymtastic.domain.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.user.UserRepository
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.domain.utils.UserConverter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: UserRepository){
    suspend fun getUserByEmail(email: String): UserModel {
        return UserConverter.toModel(userRepository.getUser(email).first())
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