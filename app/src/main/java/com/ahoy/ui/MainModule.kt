package com.ahoy.ui

import com.ahoy.api.GraphqlRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class MainModule {

    companion object{

        @Provides
        @JvmStatic
        fun provideMainViewModel(repository: GraphqlRepository): MainViewModel {
            return MainViewModel(repository)
        }
    }
}