package it.matteo.gymtastic.domain.utils

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object TrainingCardConverter {
    fun toModel(exercises: List<ExerciseModel>, trainingCardEntity: TrainingCardEntity): TrainingCardModel {
        val createdAt = trainingCardEntity.createdAt.toDate()

        return TrainingCardModel(
            id = trainingCardEntity.id,
            exercises = exercises,
            userId = trainingCardEntity.userId,
            createdAt = convertToLocalDateTimeViaMillisecond(createdAt) ?: LocalDateTime.now()
        )
    }

    fun convertToLocalDateTimeViaMillisecond(dateToConvert: Date): LocalDateTime? {
        return Instant.ofEpochMilli(dateToConvert.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun toEntity(model: TrainingCardModel): TrainingCardEntity {
        return TrainingCardEntity(
            id = model.id,
            exercises = model.exercises.map { it.id },
            userId = model.userId,
            createdAt = Timestamp.now()
        )
    }
}