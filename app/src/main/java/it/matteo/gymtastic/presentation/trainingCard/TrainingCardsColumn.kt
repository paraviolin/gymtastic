package it.matteo.gymtastic.presentation.trainingCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.presentation.Screens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingCardsColumn(list: List<TrainingCardModel>, navHostController: NavHostController) {
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 56.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "WORKOUTS HISTORY",
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
        )
        Text(
            text = "Tap to see the details",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list, itemContent = { card ->
                Card(
                    modifier = Modifier.padding(
                        horizontal = 55.dp,
                        vertical = 8.dp
                    ),
                    elevation = 30.dp,
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = MaterialTheme.colors.secondary,
                    onClick = {
                        navHostController.navigate("${Screens.TrainingCard.name}/${card.id}")
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Workout n°${list.indexOf(card) + 1}",
                            style = MaterialTheme.typography.body2
                        )
                        Text(text = "Created the ${card.createdAt.toLocalDate()}")
                    }
                }
            })
        }
    }
}