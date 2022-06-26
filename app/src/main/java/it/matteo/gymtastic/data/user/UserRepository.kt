package it.matteo.gymtastic.data.user

import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.user.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addUser(userEntity: UserEntity): Flow<Response<Void?>>

    suspend fun deleteUser(userEntity: UserEntity): Flow<Response<Void?>>

    suspend fun updateUser(userEntity: UserEntity): Flow<Response<Void?>>

    suspend fun getUser(id: String): Flow<Response<UserEntity>>
}