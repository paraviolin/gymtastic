package it.matteo.gymtastic.domain.exercise.model

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