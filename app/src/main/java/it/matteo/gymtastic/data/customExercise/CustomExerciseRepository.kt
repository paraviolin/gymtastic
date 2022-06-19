package it.matteo.gymtastic.data.customExercise

import it.matteo.gymtastic.data.customExercise.entity.CustomExerciseEntity

interface CustomExerciseRepository {
    fun getAllCustomExercises(): List<CustomExerciseEntity>

    fun getCustomExercise(id: String): CustomExerciseEntity?

    fun addCustomExercise(customExercise: CustomExerciseEntity)

    fun updateCustomExercise(customExercise: CustomExerciseEntity)

    fun removeCustomExercise(customExercise: CustomExerciseEntity)
}