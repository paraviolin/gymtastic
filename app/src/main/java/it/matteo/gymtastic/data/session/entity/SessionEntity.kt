package it.matteo.gymtastic.data.session.entity

import com.google.firebase.Timestamp

data class SessionEntity(
    val id: String,
    val trainingCardId: String,
    val userId: String,
    val createdAt: Timestamp
)
