package it.matteo.gymtastic.data.workout.entity

import it.matteo.gymtastic.domain.exercise.model.ExerciseModel

data class WorkoutEntity(
    val id: String,
    val exerciseModels: List<ExerciseModel>,
    val name: String,
    val idUser: String,
)
