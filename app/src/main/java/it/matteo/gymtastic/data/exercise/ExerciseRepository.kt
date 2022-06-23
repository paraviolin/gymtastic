package it.matteo.gymtastic.data.exercise

import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity

interface ExerciseRepository {
    fun getAllExercises(): List<ExerciseEntity>

    fun getExercise(exerciseId: String): ExerciseEntity?

    fun addExercise(exerciseEntity: ExerciseEntity)

    fun updateExercise(exerciseEntity: ExerciseEntity)

    fun deleteExercise(exerciseEntity: ExerciseEntity)

}