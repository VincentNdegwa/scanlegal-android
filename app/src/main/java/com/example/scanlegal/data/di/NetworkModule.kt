package com.example.scanlegal.data.di

import com.example.scanlegal.data.local.preferences.DataStoreManager
import com.example.scanlegal.data.remote.api.ApiService
import com.example.scanlegal.data.remote.api.MoshiFactory
import com.example.scanlegal.data.remote.interceptor.JwtAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    private const val BASE_URL = "https://your-api-base-url.com/" // TODO: Replace with actual API URL
    private const val DB_PASSPHRASE = "your-secure-passphrase" // TODO: Replace with secure passphrase
    
    @Provides
    @Singleton
    fun provideJwtAuthInterceptor(dataStoreManager: DataStoreManager): JwtAuthInterceptor {
        return JwtAuthInterceptor {
            // Get Firebase token from DataStore
            // This will be implemented when Firebase is integrated
            null
        }
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(jwtAuthInterceptor: JwtAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(jwtAuthInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshiFactory: MoshiFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshiFactory.provideMoshi()))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideSqlCipherSupportFactory(): SupportFactory {
        return SupportFactory(SQLiteDatabase.getBytes(DB_PASSPHRASE.toCharArray()))
    }
}
