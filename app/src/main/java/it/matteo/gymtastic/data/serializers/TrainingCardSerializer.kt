package it.matteo.gymtastic.data.serializers

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity


object TrainingCardSerializer {
    fun toMap(trainingCardEntity: TrainingCardEntity): Map<String, Any> {
        return hashMapOf(
            "id" to trainingCardEntity.id,
            "userId" to trainingCardEntity.userId,
            "exercises" to trainingCardEntity.exercises,
            "createdAt" to trainingCardEntity.createdAt
        )
    }

    fun fromMap(trainingCardMap: Map<String, Any>): TrainingCardEntity {
        val exercisesId = trainingCardMap["exercises"] as List<*>
        val exercises = mutableListOf<String>()

        exercisesId.forEach { element ->
            if (element != null && element::class.simpleName?.contains("String") == true) {
                val exercise = element as String
                exercises.add(exercise)
            }
        }

        val createdAt = if (trainingCardMap["createdAt"] != null)
            (trainingCardMap["createdAt"] as Timestamp)
        else Timestamp.now()

        return TrainingCardEntity(
            id = if (trainingCardMap["id"] != null) trainingCardMap["id"] as String else "",
            userId = if (trainingCardMap["userId"] != null) trainingCardMap["userId"] as String else "",
            exercises = exercises,
            createdAt = createdAt
        )
    }
}