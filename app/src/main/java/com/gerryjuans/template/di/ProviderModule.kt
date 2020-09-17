package com.gerryjuans.template.di

import com.gerryjuans.template.api.GithubRepoProvider
import dagger.Module
import dagger.Provides

@Module
class ProviderModule {

    @Provides
    fun providerGithubRepoProvider() = GithubRepoProvider()
}