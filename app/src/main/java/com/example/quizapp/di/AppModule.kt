package com.example.quizapp.di

import com.example.quizapp.data.api.QuizApi
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.domain.use_case.GetQuizUseCase
import com.example.quizapp.utils.Config.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGetQuizUseCase(repository: QuizRepository): GetQuizUseCase =
        GetQuizUseCase(repository)

    @Provides
    fun provideQuizRepository(api: QuizApi): QuizRepository = QuizRepositoryImpl(api)

    @Provides
    fun provideQuizApi(retrofit: Retrofit): QuizApi = retrofit.create(QuizApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(converterFactory).client(client)
            .build()

    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .connectTimeout(3600L, TimeUnit.SECONDS)
            .writeTimeout(3600L, TimeUnit.SECONDS)
            .readTimeout(3600L, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}