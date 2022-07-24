package it.matteo.gymtastic.domain.session.model

data class SessionExerciseModel(
    val id: String,
    val completed: Boolean,
    val note: String,
    val exerciseId: String
)
