package edu.itvo.roompersistence.presentation.stadium.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.itvo.roompersistence.presentation.stadium.components.StadiumItem
import edu.itvo.roompersistence.presentation.stadium.event.StadiumListEvent
import edu.itvo.roompersistence.presentation.stadium.viewmodel.StadiumListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StadiumListScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: StadiumListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stadiums") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Stadium")
            }
        }
    ) { padding ->

        if (uiState.stadiums.isEmpty()) {
            EmptyStadiumScreen(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(uiState.stadiums, key = { it.id }) { stadium ->
                    StadiumItem(
                        stadium  = stadium,
                        onEdit   = { onNavigateToEdit(stadium.id) },
                        onDelete = { viewModel.onEvent(StadiumListEvent.OnDelete(stadium)) }
                    )
                }
            }
        }
    }
}