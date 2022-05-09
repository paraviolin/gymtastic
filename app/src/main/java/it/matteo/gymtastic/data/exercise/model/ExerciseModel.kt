package it.matteo.gymtastic.data.exercise.model

import kotlin.time.Duration

enum class ExerciseType {
    aerobic,
    anaerobic
}

data class ExerciseModel(val duration: Duration, val name: String, val repetition: Int, val type: ExerciseType)