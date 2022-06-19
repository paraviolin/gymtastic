package it.matteo.gymtastic.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.common.BottomNavigationBar

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome ${authViewModel.user.value?.email}")
            Button(onClick = {
                // TODO remove me
                val exerciseDto = hashMapOf(
                    "duration" to "10",
                    "name" to "squat",
                    "repetition" to "4",
                    "type" to "false",
                )
                val db = Firebase.firestore
                db.collection("exercise").document("esercizio1")
                    .set(exerciseDto)
                    .addOnSuccessListener { Log.d("Success", "yayy!") }
                    .addOnFailureListener { Log.d("Failure", "noo!")}
            }) {}
        }
    }
}