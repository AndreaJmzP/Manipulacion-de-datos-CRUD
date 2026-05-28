package edu.itvo.roompersistence.domain.usecase

import javax.inject.Inject

data class StadiumUseCases @Inject constructor(
    val addStadium: AddStadiumUseCase,
    val updateStadium: UpdateStadiumUseCase,
    val deleteStadium: DeleteStadiumUseCase,
    val getStadiums: GetStadiumsUseCase
)
