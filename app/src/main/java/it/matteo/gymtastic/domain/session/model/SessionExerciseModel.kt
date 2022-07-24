package it.matteo.gymtastic.domain.session.model

import java.time.LocalDateTime

data class SessionExerciseModel(
    val id: String,
    val completed: Boolean,
    val note: String,
    val exerciseId: String,
    val createdAt: LocalDateTime? = null
)
