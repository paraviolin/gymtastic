package it.matteo.gymtastic.data.session

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.serializers.SessionExerciseSerializer
import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SessionExerciseRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    SessionExerciseRepository {
    private val _sessionExerciseRepositoryName = "session_exercise"

    override fun addSessionExercise(sessionExerciseEntity: SessionExerciseEntity) {
        val sessionExerciseDto = SessionExerciseSerializer.toMap(sessionExerciseEntity)

        db.collection(_sessionExerciseRepositoryName)
            .document(sessionExerciseEntity.id)
            .set(sessionExerciseDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override suspend fun getSessionExercise(id: String): Flow<SessionExerciseEntity?> =
        callbackFlow {
            db.collection(_sessionExerciseRepositoryName)
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener {
                    trySend(SessionExerciseSerializer.fromMap(it.first().data))
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
            awaitClose()
        }

    override suspend fun getAllSessionExercises(sessionId: String): Flow<List<SessionExerciseEntity>> =
        callbackFlow {
            val exercises = mutableListOf<SessionExerciseEntity>()

            db.collection(_sessionExerciseRepositoryName)
                .whereEqualTo("sessionId", sessionId)
                .get()
                .addOnSuccessListener {
                    it.forEach { exercise ->
                        exercises.add(SessionExerciseSerializer.fromMap(exercise.data))
                    }
                    trySend(exercises)
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }

            awaitClose()
        }

    override suspend fun deleteSessionExercise(id: String): Flow<Void?> = callbackFlow {
        db.collection(_sessionExerciseRepositoryName)
            .document(id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        awaitClose()
    }
}