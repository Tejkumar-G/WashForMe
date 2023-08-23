package com.example.washforme.di

import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.featureAuth.data.dataSource.AuthApi
import com.example.washforme.featureAuth.data.repository.AuthRepositoryImpl
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(api: AuthApi, preferenceManager: PreferenceManagerImpl): AuthRepository =
        AuthRepositoryImpl(api, preferenceManager)

//    @Provides
//    @Singleton
//    fun provideAuthUseCase(authApi: AuthApi) = null
}