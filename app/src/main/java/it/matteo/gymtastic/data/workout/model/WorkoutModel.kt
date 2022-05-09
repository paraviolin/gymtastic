package it.matteo.gymtastic.data.workout.model

import it.matteo.gymtastic.data.exercise.model.ExerciseModel

data class WorkoutModel(val exerciseModels: List<ExerciseModel>, val name: String)
