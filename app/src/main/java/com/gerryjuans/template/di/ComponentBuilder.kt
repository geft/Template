package com.gerryjuans.template.di

import android.content.Context

fun buildComponent(context: Context): AppComponent =
    DaggerAppComponent.builder()
        .providerModule(ProviderModule(context))
        .build()