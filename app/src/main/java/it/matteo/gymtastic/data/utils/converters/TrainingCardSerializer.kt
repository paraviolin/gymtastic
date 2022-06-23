package it.matteo.gymtastic.data.utils.converters

import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity

object TrainingCardSerializer {
    fun toMap(trainingCardEntity: TrainingCardEntity): Map<String, String> = hashMapOf(
        "id" to trainingCardEntity.id,
        "userId" to trainingCardEntity.userId,
        "createdAt" to trainingCardEntity.createdAt
    )

    fun fromMap(trainingCardMap: Map<String, Any>): TrainingCardEntity {
     return TrainingCardEntity(
         id = if (trainingCardMap["id"] != null) trainingCardMap["id"] as String else "",
         userId = if (trainingCardMap["userId"] != null) trainingCardMap["userId"] as String else "",
         createdAt = if (trainingCardMap["createdAt"] != null) trainingCardMap["createdAt"] as String else ""
     )
    }
}