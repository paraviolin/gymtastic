package it.matteo.gymtastic.data.utils.serializers

import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity

object TrainingCardSerializer {
    fun toMap(trainingCardEntity: TrainingCardEntity): Map<String, Any> {
        val exercises = trainingCardEntity.exercises.map {
            "${it["id"]}"
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
        val exercises = mutableListOf<Map<String, Any?>>()

        exercisesId.forEach { element ->
            if (element != null && element::class.simpleName?.contains("Map") == true) {
                val map = element as Map<*, *>
                exercises.add(
                    hashMapOf(
                        "id" to map["id"],
                        "name" to map["name"],
                        "duration" to map["duration"]
                    )
                )
            }
        }

        return TrainingCardEntity(
            id = if (trainingCardMap["id"] != null) trainingCardMap["id"] as String else "",
            userId = if (trainingCardMap["userId"] != null) trainingCardMap["userId"] as String else "",
            exercises = exercises,
            createdAt = if (trainingCardMap["createdAt"] != null) trainingCardMap["createdAt"] as String else ""
        )
    }
}