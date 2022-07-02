package it.matteo.gymtastic.domain.trainingCard.model

import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import java.time.LocalDateTime

data class TrainingCardModel(
    val id: String,
    val userId: String,
    val exercises: List<ExerciseModel>,
    val createdAt: LocalDateTime,
)