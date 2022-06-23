package it.matteo.gymtastic.data.utils.converters

import it.matteo.gymtastic.data.customExercise.entity.CustomExerciseEntity
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.exercise.model.ExerciseType
import kotlin.time.Duration

object ExerciseConverter {
    fun modelToEntity(exerciseModel: ExerciseModel): ExerciseEntity = ExerciseEntity(
        name = exerciseModel.name,
        type = exerciseModel.type.name,
        description = exerciseModel.description,
        id = exerciseModel.exerciseId
    )

    fun modelToCustomEntity(exerciseModel: ExerciseModel): CustomExerciseEntity =
        CustomExerciseEntity(
            id = exerciseModel.customExerciseId,
            idExercise = exerciseModel.exerciseId,
            duration = exerciseModel.duration.toIsoString(),
            repetition = exerciseModel.repetition,
            notes = exerciseModel.notes
        )

    fun entityToModel(
        exerciseEntity: ExerciseEntity,
        customExerciseEntity: CustomExerciseEntity
    ): ExerciseModel {
        return ExerciseModel(
            duration = Duration.parseIsoString(customExerciseEntity.duration),
            name = exerciseEntity.name,
            repetition = customExerciseEntity.repetition,
            type = ExerciseType.valueOf(exerciseEntity.type),
            description = exerciseEntity.description,
            notes = customExerciseEntity.notes,
            exerciseId = exerciseEntity.id,
            customExerciseId = customExerciseEntity.id
        )
    }
}