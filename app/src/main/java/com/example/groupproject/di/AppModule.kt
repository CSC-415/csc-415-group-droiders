package com.example.groupproject.di

import com.example.groupproject.data.DinoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDinoApi(): DinoApi{
        return Retrofit.Builder()
            .baseUrl("https://dino.com")
            .build()
            .create(DinoApi::class.java)
    }
}