package it.matteo.gymtastic.data.session.entity

data class SessionExerciseEntity(
    val id: String,
    val sessionId: String,
    val exerciseId: String,
    val completed: Boolean,
    val note: String
)
