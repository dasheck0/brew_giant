package dr.achim.brewgiant.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dr.achim.brewgiant.data.remote.PunkApi
import dr.achim.brewgiant.data.remote.datasource.BeerRemoteDataSource
import dr.achim.brewgiant.data.remote.datasource.BeerRemoteDataSourceImpl
import dr.achim.brewgiant.data.remote.repository.BeerRepositoryImpl
import dr.achim.brewgiant.domain.repository.BeerRepository
import dr.achim.brewgiant.domain.usecase.GetBeer
import dr.achim.brewgiant.domain.usecase.GetBeers
import dr.achim.brewgiant.domain.usecase.GetRandomBeer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBeerRemoteDataSource(api: PunkApi): BeerRemoteDataSource {
        return BeerRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideBeerRepository(dataSource: BeerRemoteDataSource): BeerRepository {
        return BeerRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetBeersUseCase(repository: BeerRepository) = GetBeers(repository)

    @Provides
    @Singleton
    fun provideGetRandomBeerUseCase(repository: BeerRepository) = GetRandomBeer(repository)

    @Provides
    @Singleton
    fun provideGetBeerUseCase(repository: BeerRepository) = GetBeer(repository)
}