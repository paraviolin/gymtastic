package it.matteo.gymtastic.data.user

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.google.firebase.firestore.FirebaseFirestore
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

    override suspend fun deleteUser(userEntity: UserEntity) = callbackFlow<Void?> {
        db.collection(_userDocumentName)
            .document(userEntity.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
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

    override suspend fun getUserById(id: String) = callbackFlow {

        db.collection(_userDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                trySend((UserSerializer.fromMap(it.first().data)))
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }

    override suspend fun getCustomers() = callbackFlow {
        db.collection(_userDocumentName)
            .whereEqualTo("role", "customer")
            .get()
            .addOnSuccessListener {
                val result = it.map { element ->
                    UserSerializer.fromMap(element.data)
                }
                trySend(result)
            }
            .addOnFailureListener { throw FirebaseConnectionException() }
        awaitClose()
    }


}