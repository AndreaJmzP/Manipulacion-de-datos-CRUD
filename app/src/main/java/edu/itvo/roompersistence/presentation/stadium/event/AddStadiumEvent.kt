package edu.itvo.roompersistence.presentation.stadium.event

sealed class AddStadiumEvent {
    data class OnNameChange(val value: String)       : AddStadiumEvent()
    data class OnCityChange(val value: String)       : AddStadiumEvent()
    data class OnCountryChange(val value: String)    : AddStadiumEvent()
    data class OnCapacityChange(val value: String)   : AddStadiumEvent()
    data class OnSurfaceChange(val value: String)    : AddStadiumEvent()
    data class OnYearBuiltChange(val value: String)  : AddStadiumEvent()
    data class OnPhotoChange(val value: String?)     : AddStadiumEvent()
    data object OnSave                               : AddStadiumEvent()
    data object OnClear                              : AddStadiumEvent()
}