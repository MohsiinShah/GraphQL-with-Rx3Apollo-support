package com.ahoy.di

import com.ahoy.data.ApolloClientImpl
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
        return ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .okHttpClient(okHttp.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApolloClientImpl(apolloClient: ApolloClient): com.ahoy.data.ApolloClient {
        return ApolloClientImpl(apolloClient)
    }
}