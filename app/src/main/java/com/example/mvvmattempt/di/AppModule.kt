package com.example.mvvmattempt.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmattempt.data.source.api.BASE_URL
import com.example.mvvmattempt.data.source.api.PlaceholderApi
import com.example.mvvmattempt.data.source.db.AppDatabase
import com.example.mvvmattempt.data.source.db.PictureDao
import com.example.mvvmattempt.data.source.db.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getPlaceholderApi(client: OkHttpClient): PlaceholderApi {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)  // logging
            .baseUrl(BASE_URL)
            .build()
            .create(PlaceholderApi::class.java)
    }

    @Singleton
    @Provides
    fun getClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun getInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return interceptor
    }

    @Singleton
    @Provides
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun getPictureDao(db: AppDatabase): PictureDao {
        return db.pictureDao()
    }

    @Singleton
    @Provides
    fun getPostDao(db: AppDatabase): PostDao {
        return db.postDao()
    }
}