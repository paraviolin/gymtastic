package it.matteo.gymtastic.data.user

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.Response.*
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.data.utils.serializers.UserSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : UserRepository {
    private val _userDocumentName = "users"

    override fun addUser(userEntity: UserEntity) {
        val userDto = UserSerializer.toMap(userEntity)

        db.collection(_userDocumentName)
            .document(userEntity.id)
            .set(userDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override suspend fun deleteUser(userEntity: UserEntity) = flow<Response<Void?>> {
        emit(Loading)

        val result = db.collection(_userDocumentName)
            .document(userEntity.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
            .result

        emit(Success(result))
    }

    override fun updateUser(userEntity: UserEntity) = addUser(userEntity)

    override suspend fun getUser(email: String) = callbackFlow {

        db.collection(_userDocumentName)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener {
                trySend((UserSerializer.fromMap(it.first().data)))
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }


}