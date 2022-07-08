package it.matteo.gymtastic.domain.user.model

import java.time.LocalDateTime

data class UserModel(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val createdAt: LocalDateTime,
    val role: GymRole
)

enum class GymRole {
    customer,
    trainer
}
