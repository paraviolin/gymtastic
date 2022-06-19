package it.matteo.gymtastic.data.trainingCard

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import javax.inject.Inject

class TrainingCardRepositoryImpl @Inject private constructor(val db: FirebaseFirestore) :
    TrainingCardRepository {
    private val _trainingCardDocumentName = "training_card"

    override fun addTrainingCard(trainingCardEntity: TrainingCardEntity) {
        db.collection()
    }

    override fun getTrainingCard(id: String): TrainingCardEntity {
        TODO("Not yet implemented")
    }

    override fun getAllTrainingCards(userId: String): List<TrainingCardEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteTrainingCard(id: String) {
        TODO("Not yet implemented")
    }
}