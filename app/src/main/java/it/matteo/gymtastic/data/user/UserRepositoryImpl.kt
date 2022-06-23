package it.matteo.gymtastic.data.user

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.user.entity.UserEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject private constructor(private val db: FirebaseFirestore): UserRepository {
    private val _userDocumentName = "user"

    override fun addUser(userEntity: UserEntity) {

    }

    override fun deleteUser(userEntity: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun updateUser(userEntity: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun getUser(email: String): UserEntity {
        TODO("Not yet implemented")
    }
}