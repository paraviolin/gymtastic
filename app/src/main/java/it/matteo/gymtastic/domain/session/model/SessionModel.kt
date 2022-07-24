package it.matteo.gymtastic.domain.session.model

import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import java.time.LocalDateTime

data class SessionModel(
    val id: String,
    val userId: String,
    val trainingId: String,
    val createdAt: LocalDateTime
)
