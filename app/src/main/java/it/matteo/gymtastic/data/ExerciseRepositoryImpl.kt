package it.matteo.gymtastic.data

import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.InternalChannelz.id
import it.matteo.gymtastic.data.exercise.ExerciseRepository
import it.matteo.gymtastic.data.exercise.entity.CustomExerciseEntity
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.data.exercise.model.ExerciseModel
import it.matteo.gymtastic.data.exercise.utils.ExerciseConverter
import it.matteo.gymtastic.data.exercise.utils.ExerciseSerializer
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject private constructor(private val db: FirebaseFirestore) :
    ExerciseRepository {
    private val _exerciseDocumentName = "exercise"
    private val _customExerciseDocumentName = "customExercise"

    override fun getAllExercises(): List<ExerciseModel> {
        val customExercises = mutableListOf<CustomExerciseEntity>()
        val exercises = mutableListOf<ExerciseEntity>()

        db.collection(_exerciseDocumentName)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach { document ->
                    exercises.add(ExerciseSerializer.exerciseFromMap(document.data))
                }
            }

        db.collection(_customExerciseDocumentName)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach {
                    customExercises.add(ExerciseSerializer.customExerciseFromMap(it.data))
                }
            }

        return customExercises.map { customEx->
            val exercise = exercises.find {
                it.id == customEx.idExercise
            }

            exercise?.let { ExerciseConverter.entityToModel(it, customEx) }!!
        }
    }

    override fun getExercise(exerciseId: String): ExerciseModel? {
        var customExercise: CustomExerciseEntity? = null
        var exerciseEntity: ExerciseEntity? = null

        db.collection(_customExerciseDocumentName)
            .whereEqualTo("customExerciseId", exerciseId)
            .get()
            .addOnSuccessListener { customEx ->
                if (customEx.documents.isEmpty()) return@addOnSuccessListener
                customExercise = ExerciseSerializer.customExerciseFromMap(customEx.documents.first().data!!)

                db.collection(_exerciseDocumentName)
                    .whereEqualTo("id", customExercise!!.id)
                    .get()
                    .addOnSuccessListener { exercise ->
                        exerciseEntity = ExerciseSerializer.exerciseFromMap(exercise.documents.first().data!!)
                    }
            }

        if (exerciseEntity != null && customExercise != null)
            return ExerciseConverter.entityToModel(exerciseEntity!!, customExercise!!)
        return null
    }

    override fun addExercise(exerciseModel: ExerciseModel) {
        TODO("Not yet implemented")
    }

    override fun removeExercise(exerciseModel: ExerciseModel) {
        TODO("Not yet implemented")
    }
}