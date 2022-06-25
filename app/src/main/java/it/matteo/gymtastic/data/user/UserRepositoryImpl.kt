package it.matteo.gymtastic.data.user

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.data.utils.serializers.UserSerializer
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val db: FirebaseFirestore): UserRepository {
    private val _userDocumentName = "user"

    override fun addUser(userEntity: UserEntity) {
        val userDto = UserSerializer.toMap(userEntity)

        db.collection(_userDocumentName)
            .document(userEntity.id)
            .set(userDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override fun deleteUser(userEntity: UserEntity) {
        db.collection(_userDocumentName)
            .document(userEntity.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override fun updateUser(userEntity: UserEntity) {
        addUser(userEntity)
    }

    override fun getUser(id: String): UserEntity? {
        var user: UserEntity? = null

        db.collection(_userDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                user = UserSerializer.fromMap(it.first().data)
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        return user
    }
}