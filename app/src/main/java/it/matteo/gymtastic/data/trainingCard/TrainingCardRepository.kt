package it.matteo.gymtastic.data.trainingCard

import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import kotlinx.coroutines.flow.Flow

interface TrainingCardRepository {
    suspend fun addTrainingCard(trainingCardEntity: TrainingCardEntity): Flow<Response<Void?>>

    suspend fun getTrainingCard(id: String): Flow<TrainingCardEntity?>

    suspend fun getLastTrainingCard(userId: String): Flow<TrainingCardEntity?>

    suspend fun getAllTrainingCards(userId: String): Flow<List<TrainingCardEntity>>

    suspend fun deleteTrainingCard(id: String): Flow<Response<Void?>>
}