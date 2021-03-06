package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.depinjection.AppModule
import com.example.animals.depinjection.CONTEXT_APP
import com.example.animals.depinjection.TypeOfContext
import com.example.animals.model.AnimalApiService
import com.example.animals.model.AnimalModel
import com.example.animals.model.ApiKey
import com.example.animals.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*using AndroidViewModel instead of ViewModel, to be able to get the application context,
    not the activity context, the application context is not part of view and it's has a lifetime
    which is the app lifetime. It's the default for MVVM pattern
*/
class ListViewModel(application: Application) : AndroidViewModel(application) {

    /* setting up config to be able to test this component*/
    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

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

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs: SharedPreferencesHelper

    private var invalidApiKey = false
    private var injected = false

    /* verification to see if i'm in a test mode or running in production */
    fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun refresh() {
        inject()
        invalidApiKey = false
        val key = prefs.getApiKey()
        loading.value = true

        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        inject()
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if (key.key.isEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AnimalModel>>() {
                    override fun onSuccess(list: List<AnimalModel>) {
                        loadError.value = false
                        loading.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            loadError.value = true
                            animals.value = null
                        }
                    }
                })
        )
    }
}

/*val a1 = AnimalModel("cat")
        val a2 = AnimalModel("dog")
        val a3 = AnimalModel("fish")
        val a4 = AnimalModel("tiger")
        val a5 = AnimalModel("bird")
        val a6 = AnimalModel("koala")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)
        animals.value = animalList
        loadError.value = false
        loading.value = false*/