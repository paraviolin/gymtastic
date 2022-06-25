package it.matteo.gymtastic.data.user

import it.matteo.gymtastic.data.user.entity.UserEntity

interface UserRepository {
    fun addUser(userEntity: UserEntity)

    fun deleteUser(userEntity: UserEntity)

    fun updateUser(userEntity: UserEntity)

    fun getUser(id: String): UserEntity?
}