package it.matteo.gymtastic.data.trainingCard

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.data.utils.converters.TrainingCardSerializer
import javax.inject.Inject

class TrainingCardRepositoryImpl @Inject private constructor(val db: FirebaseFirestore) :
    TrainingCardRepository {
    private val _trainingCardDocumentName = "training_card"

    override fun addTrainingCard(trainingCardEntity: TrainingCardEntity) {
        val trainingCardDto = TrainingCardSerializer.toMap(trainingCardEntity)

        db.collection(_trainingCardDocumentName)
            .document(trainingCardEntity.id)
            .set(trainingCardDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override fun getTrainingCard(id: String): TrainingCardEntity? {
        var trainingCardEntity: TrainingCardEntity? = null

        db.collection(_trainingCardDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                trainingCardEntity = TrainingCardSerializer.fromMap(it.first().data)
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
        return trainingCardEntity
    }

    override fun getAllTrainingCards(userId: String): List<TrainingCardEntity> {
        val trainingCards = mutableListOf<TrainingCardEntity>()

        db.collection(_trainingCardDocumentName)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach { document ->
                    trainingCards.add(TrainingCardSerializer.fromMap(document.data))
                }
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }

        return trainingCards
    }

    override fun deleteTrainingCard(id: String) {
        db.collection(_trainingCardDocumentName)
            .document(id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }
}