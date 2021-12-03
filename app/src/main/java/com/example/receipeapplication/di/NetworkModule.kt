package com.example.receipeapplication.di

import com.example.receipeapplication.util.Constants.Companion.BASE_URL
import com.example.receipeapplication.data.network.FoodRecipesApi
import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //all those bindings inside this network module will be available in an application component.
//this network module where we're going to tell Hilt how to provide retrofit builder to our remote data source.
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()

    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}