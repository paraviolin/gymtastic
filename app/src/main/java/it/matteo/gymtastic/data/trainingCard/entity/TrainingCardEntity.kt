package it.matteo.gymtastic.data.trainingCard.entity

data class TrainingCardEntity(
    val id: String,
    val userId: String,
    val exercises: List<String>,
    val createdAt: String
)
