package it.matteo.gymtastic.domain.trainingCard

import it.matteo.gymtastic.data.exercise.ExerciseRepository
import it.matteo.gymtastic.data.trainingCard.TrainingCardRepository
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.utils.ExerciseConverter
import it.matteo.gymtastic.domain.utils.TrainingCardConverter
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class TrainingCardService @Inject constructor(
    private val trainingCardRepository: TrainingCardRepository,
    private val exerciseRepository: ExerciseRepository
) {
    suspend fun getTrainingCard(cardId: String): TrainingCardModel? {
        val trainingCard = trainingCardRepository.getTrainingCard(cardId).first() ?: return null

        val exercises = getExerciseList(trainingCard)

        return TrainingCardConverter.toModel(exercises, trainingCard)
    }

    suspend fun getLastTrainingCard(userId: String): TrainingCardModel? {
        val trainingCards = trainingCardRepository.getAllTrainingCards(userId).first() ?: return null

        val lastTrainingCard = trainingCards.maxByOrNull { it.createdAt }!!

        val exercises = getExerciseList(lastTrainingCard)

        return TrainingCardConverter.toModel(exercises, lastTrainingCard)
    }

    suspend fun getAllTrainingCards(userId: String): List<TrainingCardModel> {
        val trainingCards = trainingCardRepository.getAllTrainingCards(userId).first()

        val trainingCardModels = mutableListOf<TrainingCardModel>()

        trainingCards.forEach { card ->
            trainingCardModels.add(TrainingCardConverter.toModel(getExerciseList(card), card))
        }

        return trainingCardModels
    }

    private suspend fun getExerciseList(trainingCard: TrainingCardEntity): List<ExerciseModel> {
        val exercises = mutableListOf<ExerciseModel>()

        trainingCard.exercises.forEach { exerciseId ->
            val exercise = exerciseRepository.getExercise(exerciseId)
            exercise.first()?.let { exerciseEntity ->
                exercises.add(ExerciseConverter.toModel(exerciseEntity))
            }
        }
        return exercises
    }

    fun createTrainingCard(listExercises: List<ExerciseModel>, userId: String) {
        val trainingCard = TrainingCardModel(
            id = UUID.randomUUID().toString(),
            userId = userId,
            listExercises,
            createdAt = LocalDateTime.now()
        )

        trainingCardRepository.addTrainingCard(TrainingCardConverter.toEntity(trainingCard))
    }

}