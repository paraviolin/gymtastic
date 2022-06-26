package it.matteo.gymtastic.domain.trainingCard

import it.matteo.gymtastic.data.exercise.ExerciseRepository
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.data.trainingCard.TrainingCardRepository
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.utils.ExerciseConverter
import it.matteo.gymtastic.domain.utils.TrainingCardConverter
import kotlinx.coroutines.flow.first
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
        val trainingCard = trainingCardRepository.getLastTrainingCard(userId).first() ?: return null

        val exercises = getExerciseList(trainingCard)

        return TrainingCardConverter.toModel(exercises, trainingCard)
    }

    suspend fun getAllTrainingCards(userId: String): List<TrainingCardModel>? {
        val trainingCards = trainingCardRepository.getAllTrainingCards(userId).first() ?: return null

        val trainingCardModels = mutableListOf<TrainingCardModel>()

        trainingCards.forEach { card ->
            trainingCardModels.add(TrainingCardConverter.toModel(getExerciseList(card), card))
        }

        return trainingCardModels
    }

    private suspend fun getExerciseList(trainingCard: TrainingCardEntity): List<ExerciseModel> {
        val exercises = mutableListOf<ExerciseModel>()

        trainingCard.exercises.forEach { exerciseMap ->
            val exercise = exerciseRepository.getExercise(exerciseMap["id"] as String)
            exercise.first()?.let { exerciseEntity ->
                exercises.add(ExerciseConverter.toModel(exerciseEntity))
            }
        }
        return exercises
    }

}