package edu.itvo.roompersistence.presentation.stadium.state

import edu.itvo.roompersistence.domain.model.Stadium

data class AddStadiumUiState(
    val id: Long = 0,
    val name: String = "",
    val city: String = "",
    val country: String = "",
    val capacity: String = "",
    val surface: String = "",
    val yearBuilt: String = "",
    val photo: String? = null,
    val isEditing: Boolean = false,
    val editingStadium: Stadium? = null,
    val errorMessage: String? = null
)
