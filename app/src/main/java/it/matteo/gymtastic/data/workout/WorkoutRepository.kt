package it.matteo.gymtastic.data.workout

import it.matteo.gymtastic.data.user.model.UserModel
import it.matteo.gymtastic.data.workout.model.WorkoutModel

interface WorkoutRepository {
    fun getWorkouts(userModel: UserModel): List<WorkoutModel>

    fun addWorkout(workoutModel: WorkoutModel)

    fun removeWorkout(workoutModel: WorkoutModel)

    fun updateWorkout(workoutModel: WorkoutModel)
}