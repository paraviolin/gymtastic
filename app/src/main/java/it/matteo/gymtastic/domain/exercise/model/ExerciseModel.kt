package it.matteo.gymtastic.domain.exercise.model

import kotlin.time.Duration

enum class ExerciseType {
    Aerobic,
    Anaerobic
}

data class ExerciseModel(
    val id: String,
    val name: String,
    val description: String,
    val type: ExerciseType,
    val duration: String
)