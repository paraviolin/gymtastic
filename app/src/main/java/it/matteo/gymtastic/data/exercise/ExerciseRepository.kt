package it.matteo.gymtastic.data.exercise

import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getAllExercises(): Flow<List<ExerciseEntity>>

    suspend fun getExercise(exerciseId: String): Flow<ExerciseEntity?>

    fun addExercise(exerciseEntity: ExerciseEntity)

    fun updateExercise(exerciseEntity: ExerciseEntity)

    suspend fun deleteExercise(exerciseEntity: ExerciseEntity): Flow<Void?>
}