package it.matteo.gymtastic.domain.session

import it.matteo.gymtastic.data.session.SessionExerciseRepository
import it.matteo.gymtastic.data.session.SessionRepository
import it.matteo.gymtastic.data.session.entity.SessionEntity
import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import it.matteo.gymtastic.domain.session.model.SessionExerciseModel
import it.matteo.gymtastic.domain.session.model.SessionModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.utils.SessionConverter
import it.matteo.gymtastic.domain.utils.TrainingCardConverter
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class SessionService @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val sessionExerciseRepository: SessionExerciseRepository
) {
    fun createSession(userId: String, trainingId: String, list: List<SessionExerciseModel>) {
        val session = SessionModel(
            id = UUID.randomUUID().toString(),
            userId = userId,
            trainingId = trainingId,
            createdAt = LocalDateTime.now()
        )
        sessionRepository.addSession(SessionConverter.toEntity(session))
        list.forEach {
            sessionExerciseRepository.addSessionExercise(
                SessionConverter.toExerciseEntity(
                    it,
                    session.id
                )
            )
        }
    }

    suspend fun getAllSessions(userId: String): List<SessionModel> {
        val sessions = sessionRepository.getAllSessions(userId).first()

        val sessionModels = mutableListOf<SessionModel>()

        sessions.forEach { sessionEntity ->
            sessionModels.add(SessionConverter.toModel(sessionEntity))
        }

        return sessionModels
    }

    suspend fun getAllSessionExercises(sessionId: String): List<SessionExerciseEntity> =
        sessionExerciseRepository.getAllSessionExercises(sessionId).first()

    suspend fun getSessionExercise(exerciseId: String): SessionExerciseEntity? =
        sessionExerciseRepository.getSessionExercise(exerciseId).first()
}