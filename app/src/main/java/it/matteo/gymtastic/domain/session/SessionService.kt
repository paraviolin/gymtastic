package it.matteo.gymtastic.domain.session

import it.matteo.gymtastic.data.session.SessionExerciseRepository
import it.matteo.gymtastic.data.session.SessionRepository
import it.matteo.gymtastic.data.session.entity.SessionEntity
import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.session.model.SessionExerciseModel
import it.matteo.gymtastic.domain.session.model.SessionModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.utils.ExerciseConverter
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
        val sessionId = UUID.randomUUID().toString()
        val session = SessionModel(
            id = sessionId,
            userId = userId,
            trainingId = trainingId,
            createdAt = LocalDateTime.now()
        )
        sessionRepository.addSession(SessionConverter.toEntity(session))
        list.forEach {
            sessionExerciseRepository.addSessionExercise(
                SessionConverter.toExerciseEntity(
                    it,
                    sessionId
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

    suspend fun getSessionExercises(
        exerciseId: String,
        userId: String
    ): MutableList<SessionExerciseModel> {
        val session = sessionRepository.getAllSessions(userId).first()

        val exercises = mutableListOf<SessionExerciseModel>()

        session.forEach {
            val rawValue =
                sessionExerciseRepository.getAllSessionExercises(exerciseId, it.id)
                    .first()
            rawValue.forEach { filterSession ->
                val exercise = SessionConverter.toExerciseModel(filterSession).copy(createdAt = TrainingCardConverter.convertToLocalDateTimeViaMillisecond(it.createdAt.toDate())
                    ?: LocalDateTime.now())
                exercises.add(exercise)
            }
        }

        return exercises
    }
}

