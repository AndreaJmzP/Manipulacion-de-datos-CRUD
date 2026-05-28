package edu.itvo.roompersistence.presentation.stadium.event

sealed class AddStadiumUiEvent {
    data object StadiumSaved  : AddStadiumUiEvent()
    data class ShowError(val message: String) : AddStadiumUiEvent()
}
