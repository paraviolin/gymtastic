package it.matteo.gymtastic.data.user

import it.matteo.gymtastic.data.user.entity.UserEntity

interface UserRepository {
    fun addUser(userEntity: UserEntity)

    fun removeUser(userEntity: UserEntity)

    fun updateUser(userEntity: UserEntity)

    fun getUser(email: String): UserEntity
}