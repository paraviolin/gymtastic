package it.matteo.gymtastic.data.workout.model

import it.matteo.gymtastic.domain.exercise.model.ExerciseModel

data class WorkoutModel(
    val id: String,
    val exerciseModels: List<ExerciseModel>,
    val name: String,
    val idUser: String,
)
