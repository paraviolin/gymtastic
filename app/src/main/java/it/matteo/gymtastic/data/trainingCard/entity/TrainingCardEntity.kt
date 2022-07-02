package it.matteo.gymtastic.data.trainingCard.entity

import com.google.firebase.Timestamp

data class TrainingCardEntity(
    val id: String,
    val userId: String,
    val exercises: List<String>,
    val createdAt: Timestamp
)
