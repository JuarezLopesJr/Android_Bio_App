package com.example.animals

import com.example.animals.depinjection.ApiModule
import com.example.animals.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService) : ApiModule() {
    override fun provideAnimalApiService(): AnimalApiService = mockService
}