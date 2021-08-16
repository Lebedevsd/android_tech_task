package com.nordpass.tt.api

import com.nordpass.tt.api.backend.BackendApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    @Provides
    internal fun provideBackEndApi(retrofit: Retrofit) = retrofit.create(BackendApi::class.java)
}