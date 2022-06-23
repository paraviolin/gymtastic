package it.matteo.gymtastic.data.utils.serializers

import it.matteo.gymtastic.data.customExercise.entity.CustomExerciseEntity
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity

object ExerciseSerializer {
    fun toMap(exerciseEntity: ExerciseEntity): Map<String, String> = hashMapOf(
        "id" to exerciseEntity.id,
        "name" to exerciseEntity.name,
        "type" to exerciseEntity.type,
        "description" to exerciseEntity.description
    )

    fun toMap(customExerciseEntity: CustomExerciseEntity): Map<String, String> = hashMapOf(
        "id" to customExerciseEntity.id,
        "duration" to customExerciseEntity.duration,
        "repetition" to customExerciseEntity.repetition,
        "notes" to customExerciseEntity.notes,
        "idExercise" to customExerciseEntity.idExercise
    )

    fun exerciseFromMap(exerciseMap: Map<String, Any>): ExerciseEntity {
        return ExerciseEntity(
            id = if (exerciseMap["id"] != null) exerciseMap["id"] as String else "",
            name = if (exerciseMap["name"] != null) exerciseMap["name"] as String else "",
            type = if (exerciseMap["type"] != null) exerciseMap["type"] as String else "",
            description = if (exerciseMap["description"] != null) exerciseMap["description"] as String else ""
        )
    }

    fun customExerciseFromMap(exerciseMap: Map<String, Any>): CustomExerciseEntity {
        return CustomExerciseEntity(
            id = if (exerciseMap["id"] != null) exerciseMap["id"] as String else "",
            duration = if (exerciseMap["duration"] != null) exerciseMap["duration"] as String else "",
            repetition = if (exerciseMap["repetition"] != null) exerciseMap["repetition"] as String else "",
            notes = if (exerciseMap["notes"] != null) exerciseMap["notes"] as String else "",
            idExercise = if (exerciseMap["idExercise"] != null) exerciseMap["idExercise"] as String else ""
        )
    }
}