package it.matteo.gymtastic.data.user

import it.matteo.gymtastic.data.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun addUser(userEntity: UserEntity)

    suspend fun deleteUser(userEntity: UserEntity): Flow<Void?>

    fun updateUser(userEntity: UserEntity)

    suspend fun getUser(email: String): Flow<UserEntity>

    suspend fun getUserById(id: String): Flow<UserEntity>

    suspend fun getCustomers(): Flow<List<UserEntity>>
}