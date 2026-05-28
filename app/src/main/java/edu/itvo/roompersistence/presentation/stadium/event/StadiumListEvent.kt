package edu.itvo.roompersistence.presentation.stadium.event

import edu.itvo.roompersistence.domain.model.Stadium

sealed class StadiumListEvent {
    data class OnDelete(val stadium: Stadium) : StadiumListEvent()
    data class OnSelectForEdit(val stadium: Stadium) : StadiumListEvent()
}