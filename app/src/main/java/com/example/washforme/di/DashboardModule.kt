package com.example.washforme.di

import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.core.data.dataSource.remote.DashboardApi
import com.example.washforme.core.data.repository.DashboardRepoImpl
import com.example.washforme.core.domain.repository.DashboardRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideDashboardRepository(retrofit: Retrofit): DashboardApi =
        retrofit.create(DashboardApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(api: DashboardApi, preferenceManager: PreferenceManagerImpl): DashboardRepo =
        DashboardRepoImpl(api, preferenceManager)

//    @Provides
//    @Singleton
//    fun provideAuthUseCase(authApi: AuthApi) = null
}