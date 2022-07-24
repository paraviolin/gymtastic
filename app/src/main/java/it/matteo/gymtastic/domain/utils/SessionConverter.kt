package it.matteo.gymtastic.domain.utils

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.session.entity.SessionEntity
import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import it.matteo.gymtastic.domain.session.model.SessionExerciseModel
import it.matteo.gymtastic.domain.session.model.SessionModel
import java.time.LocalDateTime

object SessionConverter {
    fun toModel(entity: SessionEntity): SessionModel = SessionModel(
        id = entity.id,
        userId = entity.userId,
        trainingId = entity.trainingCardId,
        createdAt = TrainingCardConverter.convertToLocalDateTimeViaMillisecond(entity.createdAt.toDate())
            ?: LocalDateTime.now()
    )

    fun toEntity(model: SessionModel): SessionEntity = SessionEntity(
        id = model.id,
        userId = model.userId,
        trainingCardId = model.trainingId,
        createdAt = Timestamp.now()
    )

    fun toExerciseEntity(model: SessionExerciseModel, sessionId: String) = SessionExerciseEntity(
        id = model.id,
        sessionId = sessionId,
        completed = model.completed,
        exerciseId = model.exerciseId,
        note = model.note
    )
}