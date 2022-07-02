package it.matteo.gymtastic.data.exercise

import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getAllExercises(): Flow<List<ExerciseEntity>>

    suspend fun getExercise(exerciseId: String): Flow<ExerciseEntity?>

    suspend fun addExercise(exerciseEntity: ExerciseEntity): Flow<Response<Void?>>

    suspend fun updateExercise(exerciseEntity: ExerciseEntity): Flow<Response<Void?>>

    suspend fun deleteExercise(exerciseEntity: ExerciseEntity): Flow<Response<Void?>>

}