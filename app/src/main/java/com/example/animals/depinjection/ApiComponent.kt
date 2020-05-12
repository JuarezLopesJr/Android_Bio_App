package com.example.animals.depinjection

import com.example.animals.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    /* where to inject the functionality */
    fun inject(service: AnimalApiService)
}