package it.matteo.gymtastic.data.exercise

import it.matteo.gymtastic.data.exercise.model.ExerciseModel
import it.matteo.gymtastic.data.user.model.UserModel

interface ExerciseRepository {
    fun getExercises(userModel: UserModel): List<ExerciseModel>

    fun addExercise(exerciseModel: ExerciseModel)

    fun removeExercise(exerciseModel: ExerciseModel)
}