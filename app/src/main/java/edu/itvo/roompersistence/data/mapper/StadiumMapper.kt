package edu.itvo.roompersistence.data.mapper

import edu.itvo.roompersistence.data.local.entity.StadiumEntity
import edu.itvo.roompersistence.domain.model.Stadium

fun StadiumEntity.toDomain(): Stadium =
    Stadium(
        id       = id,
        name     = name,
        city     = city,
        country  = country,
        capacity = capacity,
        surface  = surface,
        yearBuilt = yearBuilt,
        photo    = photo
    )

fun Stadium.toEntity(): StadiumEntity =
    StadiumEntity(
        id       = id,
        name     = name,
        city     = city,
        country  = country,
        capacity = capacity,
        surface  = surface,
        yearBuilt = yearBuilt,
        photo    = photo
    )
