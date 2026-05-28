package edu.itvo.roompersistence.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.itvo.roompersistence.data.local.database.AppDatabase
import edu.itvo.roompersistence.data.local.dao.StadiumDao
import edu.itvo.roompersistence.data.repository.StadiumRepositoryImpl
import edu.itvo.roompersistence.domain.repository.StadiumRepository
import edu.itvo.roompersistence.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StadiumModule {

    @Provides
    @Singleton
    fun provideStadiumDao(database: AppDatabase): StadiumDao =
        database.stadiumDao()

    @Provides
    @Singleton
    fun provideStadiumRepository(stadiumDao: StadiumDao): StadiumRepository =
        StadiumRepositoryImpl(stadiumDao)

    @Provides
    @Singleton
    fun provideStadiumUseCases(repository: StadiumRepository): StadiumUseCases =
        StadiumUseCases(
            addStadium    = AddStadiumUseCase(repository),
            updateStadium = UpdateStadiumUseCase(repository),
            deleteStadium = DeleteStadiumUseCase(repository),
            getStadiums   = GetStadiumsUseCase(repository)
        )
}