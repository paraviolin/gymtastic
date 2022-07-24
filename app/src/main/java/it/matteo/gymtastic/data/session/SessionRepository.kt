package it.matteo.gymtastic.data.session

import it.matteo.gymtastic.data.session.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun addSession(sessionEntity: SessionEntity)

    suspend fun getSession(id: String): Flow<SessionEntity?>

    suspend fun getAllSessions(userId: String): Flow<List<SessionEntity>>

    suspend fun deleteSession(id: String): Flow<Void?>
}