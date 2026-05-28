package edu.itvo.roompersistence.presentation.stadium.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.roompersistence.domain.model.Stadium
import edu.itvo.roompersistence.domain.usecase.StadiumUseCases
import edu.itvo.roompersistence.presentation.stadium.event.AddStadiumEvent
import edu.itvo.roompersistence.presentation.stadium.event.AddStadiumUiEvent
import edu.itvo.roompersistence.presentation.stadium.state.AddStadiumUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStadiumViewModel @Inject constructor(
    private val useCases: StadiumUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddStadiumUiState())
    val uiState: StateFlow<AddStadiumUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<AddStadiumUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun loadById(id: Long) {
        viewModelScope.launch {
            val stadium = useCases.getStadiums()
                .first()
                .find { it.id == id }
                ?: return@launch

            _uiState.update {
                it.copy(
                    id             = stadium.id,
                    name           = stadium.name,
                    city           = stadium.city,
                    country        = stadium.country,
                    capacity       = stadium.capacity.toString(),
                    surface        = stadium.surface,
                    yearBuilt      = stadium.yearBuilt.toString(),
                    photo          = stadium.photo,
                    isEditing      = true,
                    editingStadium = stadium
                )
            }
        }
    }

    fun onEvent(event: AddStadiumEvent) {
        when (event) {
            is AddStadiumEvent.OnNameChange      -> _uiState.update { it.copy(name = event.value) }
            is AddStadiumEvent.OnCityChange      -> _uiState.update { it.copy(city = event.value) }
            is AddStadiumEvent.OnCountryChange   -> _uiState.update { it.copy(country = event.value) }
            is AddStadiumEvent.OnCapacityChange  -> _uiState.update { it.copy(capacity = event.value) }
            is AddStadiumEvent.OnSurfaceChange   -> _uiState.update { it.copy(surface = event.value) }
            is AddStadiumEvent.OnYearBuiltChange -> _uiState.update { it.copy(yearBuilt = event.value) }
            is AddStadiumEvent.OnPhotoChange     -> _uiState.update { it.copy(photo = event.value) }
            is AddStadiumEvent.OnSave            -> saveStadium()
            is AddStadiumEvent.OnClear           -> _uiState.update { AddStadiumUiState() }
        }
    }

    private fun saveStadium() {
        val state = _uiState.value

        if (state.name.isBlank() || state.city.isBlank() || state.country.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Name, city and country are required") }
            return
        }

        viewModelScope.launch {
            val stadium = Stadium(
                id        = state.id,
                name      = state.name.trim(),
                city      = state.city.trim(),
                country   = state.country.trim(),
                capacity  = state.capacity.toIntOrNull() ?: 0,
                surface   = state.surface.trim(),
                yearBuilt = state.yearBuilt.toIntOrNull() ?: 0,
                photo     = state.photo
            )

            if (state.isEditing) {
                useCases.updateStadium(stadium)
            } else {
                useCases.addStadium(stadium)
            }

            _uiState.update { AddStadiumUiState() }
            _uiEvent.send(AddStadiumUiEvent.StadiumSaved)
        }
    }
}