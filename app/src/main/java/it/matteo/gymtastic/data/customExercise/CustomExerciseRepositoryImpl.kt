package it.matteo.gymtastic.data.customExercise

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.customExercise.entity.CustomExerciseEntity
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.utils.ExerciseSerializer
import javax.inject.Inject

class CustomExerciseRepositoryImpl @Inject private constructor(private val db: FirebaseFirestore): CustomExerciseRepository {
    private val _customExerciseDocumentName = "custom_exercise"

    override fun getAllCustomExercises(): List<CustomExerciseEntity> {
        val exercises = mutableListOf<CustomExerciseEntity>()

        db.collection(_customExerciseDocumentName)
            .get()
            .addOnSuccessListener { documents->
                documents.forEach { document->
                    exercises.add(ExerciseSerializer.customExerciseFromMap(document.data))
                }
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

        return exercises
    }

    override fun getCustomExercise(id: String): CustomExerciseEntity? {
        var exercise: CustomExerciseEntity? = null

        db.collection(_customExerciseDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                exercise = ExerciseSerializer.customExerciseFromMap(it.first().data)
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

        return exercise
    }

    override fun addCustomExercise(customExercise: CustomExerciseEntity) {
        val exerciseDto = ExerciseSerializer.toMap(customExercise)

        db.collection(_customExerciseDocumentName)
            .document(customExercise.id)
            .set(exerciseDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override fun updateCustomExercise(customExercise: CustomExerciseEntity) {
        addCustomExercise(customExercise)
    }

    override fun removeCustomExercise(customExercise: CustomExerciseEntity) {
        db.collection(_customExerciseDocumentName)
            .document(customExercise.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }
}