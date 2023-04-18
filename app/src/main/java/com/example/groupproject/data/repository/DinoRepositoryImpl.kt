package com.example.groupproject.data.repository
import com.example.groupproject.data.DinoApi
import javax.inject.Inject

class DinoRepositoryImpl(private val api : DinoApi) : DinoRepository {

    override suspend fun doNetworkCall(){}
}