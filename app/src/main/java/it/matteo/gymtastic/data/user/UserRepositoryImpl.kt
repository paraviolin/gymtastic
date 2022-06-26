package it.matteo.gymtastic.data.user

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.Response.*
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.data.utils.serializers.UserSerializer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val db: FirebaseFirestore): UserRepository {
    private val _userDocumentName = "user"

    override suspend fun addUser(userEntity: UserEntity) = flow<Response<Void?>> {
        emit(Loading)
        val userDto = UserSerializer.toMap(userEntity)

        val result = db.collection(_userDocumentName)
            .document(userEntity.id)
            .set(userDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
            .result
        emit(Success(result))
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

    override suspend fun updateUser(userEntity: UserEntity) = flow<Response<Void?>>{
        addUser(userEntity)
    }

    override suspend fun getUser(id: String) = callbackFlow {
        trySend(Loading)

        db.collection(_userDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                trySend((Success(UserSerializer.fromMap(it.first().data))))
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }
}