package it.matteo.gymtastic.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object TrainingCardConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toModel(exercises: List<ExerciseModel>, trainingCardEntity: TrainingCardEntity): TrainingCardModel {
        val createdAt = trainingCardEntity.createdAt.toDate()

        return TrainingCardModel(
            id = trainingCardEntity.id,
            exercises = exercises,
            userId = trainingCardEntity.userId,
            createdAt = convertToLocalDateTimeViaMilisecond(createdAt) ?: LocalDateTime.now()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDateTimeViaMilisecond(dateToConvert: Date): LocalDateTime? {
        return Instant.ofEpochMilli(dateToConvert.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}