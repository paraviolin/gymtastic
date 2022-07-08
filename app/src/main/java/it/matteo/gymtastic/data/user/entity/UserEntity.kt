package it.matteo.gymtastic.data.user.entity

import com.google.firebase.Timestamp

data class UserEntity(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val createdAt: Timestamp
)
