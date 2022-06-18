package it.matteo.gymtastic.data.exercise.entity

data class ExerciseEntity(
    val id: String,
    val name: String,
    val description: String,
    val type: String
)

data class CustomExerciseEntity(
    val id: String,
    val duration: String,
    val repetition: String,
    val notes: String,
    val idExercise: String,
)

