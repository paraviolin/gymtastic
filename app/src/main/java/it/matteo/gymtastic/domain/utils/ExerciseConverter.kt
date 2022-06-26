package it.matteo.gymtastic.domain.utils

import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.exercise.model.ExerciseType

object ExerciseConverter {
    fun toModel(entity: ExerciseEntity): ExerciseModel = ExerciseModel(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        type = ExerciseType.valueOf(entity.type),
        duration = entity.duration
    )
}