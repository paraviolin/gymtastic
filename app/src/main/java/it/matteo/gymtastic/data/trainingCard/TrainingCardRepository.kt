package it.matteo.gymtastic.data.trainingCard

import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity

interface TrainingCardRepository {
    fun addTrainingCard(trainingCardEntity: TrainingCardEntity)

    fun getTrainingCard(id: String): TrainingCardEntity

    fun getAllTrainingCards(userId: String): List<TrainingCardEntity>

    fun deleteTrainingCard(id: String)
}