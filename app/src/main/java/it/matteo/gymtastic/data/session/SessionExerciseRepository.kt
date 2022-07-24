package it.matteo.gymtastic.data.session

import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import kotlinx.coroutines.flow.Flow

interface SessionExerciseRepository {
    fun addSessionExercise(sessionExerciseEntity: SessionExerciseEntity)

    suspend fun getSessionExercise(id: String): Flow<SessionExerciseEntity?>

    suspend fun getAllSessionExercises(sessionId: String): Flow<List<SessionExerciseEntity>>

    suspend fun getAllSessionExercises(exerciseId: String, sessionId: String): Flow<List<SessionExerciseEntity>>

    suspend fun deleteSessionExercise(id: String): Flow<Void?>
}