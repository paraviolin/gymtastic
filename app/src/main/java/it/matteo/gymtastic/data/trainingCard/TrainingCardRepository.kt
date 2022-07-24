package it.matteo.gymtastic.data.trainingCard

import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import kotlinx.coroutines.flow.Flow

interface TrainingCardRepository {
    fun addTrainingCard(trainingCardEntity: TrainingCardEntity)

    suspend fun getTrainingCard(id: String): Flow<TrainingCardEntity?>

    suspend fun getLastTrainingCard(userId: String): Flow<TrainingCardEntity?>

    suspend fun getAllTrainingCards(userId: String): Flow<List<TrainingCardEntity>>

    suspend fun deleteTrainingCard(id: String): Flow<Void?>
}