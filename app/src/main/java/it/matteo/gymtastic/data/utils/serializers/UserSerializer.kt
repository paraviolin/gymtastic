package it.matteo.gymtastic.data.utils.serializers

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.user.entity.UserEntity

object UserSerializer {
    fun toMap(userEntity: UserEntity): Map<String, Any> = hashMapOf(
        "id" to userEntity.id,
        "email" to userEntity.email,
        "name" to userEntity.name,
        "surname" to userEntity.surname,
        "createdAt" to userEntity.createdAt,
        "role" to userEntity.role
    )

    fun fromMap(userMap: Map<String, Any>): UserEntity {
        return UserEntity(
            id = if (userMap["id"] != null) userMap["id"] as String else "",
            email = if (userMap["email"] != null) userMap["email"] as String else "",
            surname = if (userMap["surname"] != null) userMap["surname"] as String else "",
            name = if (userMap["name"] != null) userMap["name"] as String else "",
            createdAt = if (userMap["createdAt"] != null) userMap["createdAt"] as Timestamp else Timestamp.now(),
            role = if (userMap["role"] != null) userMap["role"] as String else "",
        )
    }
}