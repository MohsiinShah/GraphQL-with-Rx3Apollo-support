package com.ahoy.di

import com.ahoy.api.GraphqlRepository
import com.ahoy.data.ApolloClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesGraphqlRepository(client: ApolloClientImpl): GraphqlRepository {
        return GraphqlRepository(client)
    }
}