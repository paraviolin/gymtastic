package it.matteo.gymtastic.data.session

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.session.entity.SessionEntity
import it.matteo.gymtastic.data.serializers.SessionSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    SessionRepository {
    private val _sessionRepositoryName = "session"

    override fun addSession(sessionEntity: SessionEntity) {
        val sessionDto = SessionSerializer.toMap(sessionEntity)

        db.collection(_sessionRepositoryName)
            .document(sessionEntity.id)
            .set(sessionDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

    }

    override suspend fun getSession(id: String): Flow<SessionEntity?> = callbackFlow {
        db.collection(_sessionRepositoryName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                trySend(SessionSerializer.fromMap(it.first().data))
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }

    override suspend fun getAllSessions(userId: String): Flow<List<SessionEntity>> = callbackFlow {
        val sessions = mutableListOf<SessionEntity>()
        db.collection(_sessionRepositoryName)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                it.forEach { session -> sessions.add(SessionSerializer.fromMap(session.data)) }
                trySend(sessions)
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }

    override suspend fun deleteSession(id: String): Flow<Void?> = callbackFlow {
        db.collection(_sessionRepositoryName)
            .document(id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }
}