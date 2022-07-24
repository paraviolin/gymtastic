package it.matteo.gymtastic.data.serializers

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.session.entity.SessionEntity

object SessionSerializer {
    fun toMap(sessionEntity: SessionEntity): Map<String, Any> {
        return hashMapOf(
            "id" to sessionEntity.id,
            "trainingCardId" to sessionEntity.trainingCardId,
            "userId" to sessionEntity.userId,
            "createdAt" to sessionEntity.createdAt
        )
    }

    fun fromMap(sessionMap: Map<String, Any>): SessionEntity {
        return SessionEntity(
            id = if (sessionMap["id"] != null) sessionMap["id"] as String else "",
            trainingCardId = if (sessionMap["trainingCardId"] != null) sessionMap["trainingCardId"] as String else "",
            userId = if (sessionMap["userId"] != null) sessionMap["userId"] as String else "",
            createdAt = if (sessionMap["createdAt"] != null) sessionMap["createdAt"] as Timestamp else Timestamp.now(),
        )
    }
}