package it.matteo.gymtastic.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.presentation.Screens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutList(card: TrainingCardModel, navHostController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
        items(card.exercises, itemContent =
        { item ->
            Card(
                modifier = Modifier.padding(
                    horizontal = 55.dp,
                    vertical = 8.dp
                ),
                elevation = 30.dp,
                shape = MaterialTheme.shapes.medium,
                backgroundColor = MaterialTheme.colors.secondary,
                onClick = {
                    navHostController.navigate("${Screens.WorkoutDetail.name}/${item.id}")
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
                        text = item.name,
                        style = MaterialTheme.typography.body2
                    )
                    Text(text = item.duration)
                }
            }

        }
        )
    }
}