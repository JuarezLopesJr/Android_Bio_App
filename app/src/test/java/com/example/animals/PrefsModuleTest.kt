package com.example.animals

import android.app.Application
import com.example.animals.depinjection.PrefsModule
import com.example.animals.utils.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper) : PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper = mockPrefs
}