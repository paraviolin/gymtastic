package it.matteo.gymtastic.domain.exercise.model

import kotlin.time.Duration

enum class ExerciseType {
    Aerobic,
    Anaerobic
}

data class ExerciseModel(
    val customExerciseId: String,
    val exerciseId: String,
    val name: String,
    val description: String,
    val type: ExerciseType,
    val duration: Duration,
    val repetition: String,
    val notes: String
)