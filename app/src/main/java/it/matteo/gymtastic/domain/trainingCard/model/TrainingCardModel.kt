package it.matteo.gymtastic.domain.trainingCard.model

import it.matteo.gymtastic.domain.exercise.model.ExerciseModel

data class TrainingCardModel(
    val id: String,
    val userId: String,
    val exercises: List<ExerciseModel>,
    val createdAt: String,
)