package it.matteo.gymtastic.presentation.customer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.customer.viewModel.CustomerViewModel
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent

@Composable
fun CustomerDetailScreen(navHostController: NavHostController, email: String?) {
    val customerDetail: CustomerViewModel = hiltViewModel()
    val customer = remember {
        customerDetail.customer
    }

    customerDetail.fetchUserDetail(email ?: "")

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier,
            title = { Text(text = "") },
            navigationIcon = {
                if (navHostController.previousBackStackEntry != null) {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )
    }) {

        if (customer.value == null) {
            LoaderComponent()
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CUSTOMER DETAILS",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                )


                Divider(
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .width(1.dp)
                )

                TextFieldComponent(
                    content = customer.value?.name ?: "",
                    labelName = "Name",
                    labelFontSize = 16.sp,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 32.dp),
                    enabled = false,
                    shape = RoundedCornerShape(20),
                )

                TextFieldComponent(
                    content = customer.value?.surname ?: "",
                    labelName = "Surname",
                    labelFontSize = 16.sp,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 32.dp),
                    enabled = false,
                    shape = RoundedCornerShape(20),
                )

                TextFieldComponent(
                    content = customer.value?.email ?: "",
                    labelName = "Email",
                    labelFontSize = 16.sp,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 32.dp),
                    enabled = false,
                    shape = RoundedCornerShape(20),
                )

                TextFieldComponent(
                    content = "${customer.value?.createdAt?.dayOfMonth} ${customer.value?.createdAt?.month} ${customer.value?.createdAt?.year}",
                    labelName = "Joined",
                    labelFontSize = 16.sp,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 32.dp),
                    enabled = false,
                    shape = RoundedCornerShape(20),
                )

                OutlinedStyledButton(
                    onClick = { navHostController.navigate("${Screens.CustomerWorkouts}/${customer.value?.id}") },
                    textLabel = "See workouts"
                )

            }
        }

    }
}