package com.gerryjuans.template.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class ProviderModule(
    private val context: Context
) {

    private companion object {
        const val SHARED_PREF_KEY = "SHARED_PREF_KEY"
    }

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
}