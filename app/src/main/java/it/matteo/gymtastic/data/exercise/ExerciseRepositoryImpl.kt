package it.matteo.gymtastic.data.exercise

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.data.utils.serializers.ExerciseSerializer
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    ExerciseRepository {
    private val _exerciseDocumentName = "exercise"

    override fun getAllExercises(): List<ExerciseEntity> {
        val exercises = mutableListOf<ExerciseEntity>()

        db.collection(_exerciseDocumentName)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach { document ->
                    exercises.add(ExerciseSerializer.exerciseFromMap(document.data))
                }
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

        return exercises
    }

    override fun getExercise(exerciseId: String): ExerciseEntity? {
        var exerciseEntity: ExerciseEntity? = null

        db.collection(_exerciseDocumentName)
            .whereEqualTo("id", exerciseId)
            .get()
            .addOnSuccessListener {
                exerciseEntity = ExerciseSerializer.exerciseFromMap(it.first().data)
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

        return exerciseEntity
    }

    override fun addExercise(exerciseEntity: ExerciseEntity) {
        val exerciseDto = ExerciseSerializer.toMap(exerciseEntity)

        db.collection(_exerciseDocumentName)
            .document(exerciseEntity.id)
            .set(exerciseDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override fun updateExercise(exerciseEntity: ExerciseEntity) {
        addExercise(exerciseEntity)
    }

    override fun deleteExercise(exerciseEntity: ExerciseEntity) {
        db.collection(_exerciseDocumentName)
            .document(exerciseEntity.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }
}