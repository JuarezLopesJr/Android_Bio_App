package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.model.AnimalModel

/*using AndroidViewModel instead of ViewModel, to be able to get the application context,
    not the activity context, the application context is not part of view and it's has a lifetime
    which is the app lifetime. It's the default for MVVM pattern
*/
class ListViewModel(application: Application) : AndroidViewModel(application) {
    /* lazy means that this data will only be instantiated when it's needed */
    val animals by lazy {
        MutableLiveData<List<AnimalModel>>()
    }

    val loadError by lazy {
        MutableLiveData<Boolean>()
    }

    val loading by lazy {
        MutableLiveData<Boolean>()
    }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = AnimalModel("cat")
        val a2 = AnimalModel("dog")
        val a3 = AnimalModel("fish")
        val a4 = AnimalModel("tiger")
        val a5 = AnimalModel("bird")
        val a6 = AnimalModel("koala")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)
        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}