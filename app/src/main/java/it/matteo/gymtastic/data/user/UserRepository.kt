package it.matteo.gymtastic.data.user

import it.matteo.gymtastic.data.user.model.UserModel

interface UserRepository {
    fun addUser(userModel: UserModel)

    fun removeUser(userModel: UserModel)

    fun updateUser(userModel: UserModel)

    fun getUser(email: String): UserModel
}