package it.matteo.gymtastic.data.serializers

import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity

object SessionExerciseSerializer {
    fun toMap(sessionExerciseEntity: SessionExerciseEntity): Map<String, Any> {
        return hashMapOf(
            "id" to sessionExerciseEntity.id,
            "exerciseId" to sessionExerciseEntity.exerciseId,
            "completed" to sessionExerciseEntity.completed,
            "note" to sessionExerciseEntity.note,
            "sessionId" to sessionExerciseEntity.sessionId
        )
    }

    fun fromMap(sessionExerciseMap: Map<String, Any>): SessionExerciseEntity {
        return SessionExerciseEntity(
            id = if (sessionExerciseMap["id"] != null) sessionExerciseMap["id"] as String else "",
            exerciseId = if (sessionExerciseMap["exerciseId"] != null) sessionExerciseMap["exerciseId"] as String else "",
            completed = if (sessionExerciseMap["completed"] != null) sessionExerciseMap["completed"] as Boolean else false,
            sessionId = if (sessionExerciseMap["sessionId"] != null) sessionExerciseMap["sessionId"] as String else "",
            note = if (sessionExerciseMap["note"] != null) sessionExerciseMap["note"] as String else "",
        )
    }
}