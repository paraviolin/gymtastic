package it.matteo.gymtastic.domain.utils

import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel

object TrainingCardConverter {
    fun toModel(exercises: List<ExerciseModel>, trainingCardEntity: TrainingCardEntity): TrainingCardModel {
        return TrainingCardModel(
            id = trainingCardEntity.id,
            exercises = exercises,
            userId = trainingCardEntity.userId,
            createdAt = trainingCardEntity.createdAt
        )
    }
}