package com.example.animals.depinjection

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.animals.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
   open fun provideSharedPreferences(app: Application): SharedPreferencesHelper = SharedPreferencesHelper(app)

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: AppCompatActivity): SharedPreferencesHelper =
        SharedPreferencesHelper(activity)
}

const val CONTEXT_APP = "application context"
const val CONTEXT_ACTIVITY = "activity context"

@Qualifier
annotation class TypeOfContext(val type:String)