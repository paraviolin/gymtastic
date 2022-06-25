package it.matteo.gymtastic.data.utils.serializers

import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity

object TrainingCardSerializer {
    fun toMap(trainingCardEntity: TrainingCardEntity): Map<String, Any> {
        val exercises = trainingCardEntity.exercises.map {
            "${it["id"]} ${it["name"]}"
        }

        return hashMapOf(
            "id" to trainingCardEntity.id,
            "userId" to trainingCardEntity.userId,
            "exercises" to exercises,
            "createdAt" to trainingCardEntity.createdAt
        )
    }

    fun fromMap(trainingCardMap: Map<String, Any>): TrainingCardEntity {
        val exercisesId = trainingCardMap["exercises"] as List<*>
        val exercises = exercisesId.map {
            val key = it as String
            hashMapOf(
                "id" to key.substringBefore(" "),
                "name" to key.substringAfter(" ")
            )
        }

        return TrainingCardEntity(
            id = if (trainingCardMap["id"] != null) trainingCardMap["id"] as String else "",
            userId = if (trainingCardMap["userId"] != null) trainingCardMap["userId"] as String else "",
            exercises = exercises,
            createdAt = if (trainingCardMap["createdAt"] != null) trainingCardMap["createdAt"] as String else ""
        )
    }
}