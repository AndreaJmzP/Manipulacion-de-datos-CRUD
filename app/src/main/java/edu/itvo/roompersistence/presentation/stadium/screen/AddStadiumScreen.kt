package edu.itvo.roompersistence.presentation.stadium.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.itvo.roompersistence.presentation.stadium.event.AddStadiumEvent
import edu.itvo.roompersistence.presentation.stadium.event.AddStadiumUiEvent
import edu.itvo.roompersistence.presentation.stadium.viewmodel.AddStadiumViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStadiumScreen(
    stadiumId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: AddStadiumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(stadiumId) {
        stadiumId?.let { viewModel.loadById(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is AddStadiumUiEvent.StadiumSaved -> onNavigateBack()
                is AddStadiumUiEvent.ShowError    -> { }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (uiState.isEditing) "Edit Stadium" else "Add Stadium")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value         = uiState.name,
                onValueChange = { viewModel.onEvent(AddStadiumEvent.OnNameChange(it)) },
                label         = { Text("Name") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            OutlinedTextField(
                value         = uiState.city,
                onValueChange = { viewModel.onEvent(AddStadiumEvent.OnCityChange(it)) },
                label         = { Text("City") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            OutlinedTextField(
                value         = uiState.country,
                onValueChange = { viewModel.onEvent(AddStadiumEvent.OnCountryChange(it)) },
                label         = { Text("Country") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            OutlinedTextField(
                value           = uiState.capacity,
                onValueChange   = { viewModel.onEvent(AddStadiumEvent.OnCapacityChange(it)) },
                label           = { Text("Capacity") },
                modifier        = Modifier.fillMaxWidth(),
                singleLine      = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value         = uiState.surface,
                onValueChange = { viewModel.onEvent(AddStadiumEvent.OnSurfaceChange(it)) },
                label         = { Text("Surface") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            OutlinedTextField(
                value           = uiState.yearBuilt,
                onValueChange   = { viewModel.onEvent(AddStadiumEvent.OnYearBuiltChange(it)) },
                label           = { Text("Year Built") },
                modifier        = Modifier.fillMaxWidth(),
                singleLine      = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            uiState.errorMessage?.let { error ->
                Text(
                    text  = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick  = { viewModel.onEvent(AddStadiumEvent.OnSave) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditing) "Update" else "Save")
            }
        }
    }
}