package it.matteo.gymtastic.domain.utils

import com.google.firebase.Timestamp
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.domain.user.model.GymRole
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.domain.utils.TrainingCardConverter.convertToLocalDateTimeViaMillisecond
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

object UserConverter {
    fun toModel(entity: UserEntity): UserModel {
        val createdAt = entity.createdAt.toDate()

        return UserModel(
            id = entity.id,
            name = entity.name,
            surname = entity.surname,
            email = entity.email,
            createdAt = convertToLocalDateTimeViaMillisecond(createdAt) ?: LocalDateTime.now(),
            role = GymRole.valueOf(entity.role)
        )
    }

    fun toEntity(model: UserModel): UserEntity {
        return UserEntity(
            id = model.id,
            name = model.name,
            surname = model.surname,
            email = model.email,
            role = model.role.name,
            createdAt = Timestamp(Date.from(model.createdAt.toInstant()))
        )
    }
}

private fun LocalDateTime.toInstant(): Instant? {
    val currentZone = ZoneId.of("Europe/London").rules.getOffset(Instant.now());
    return this.toInstant(currentZone)
}
