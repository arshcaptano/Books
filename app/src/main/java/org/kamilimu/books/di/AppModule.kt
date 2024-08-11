package org.kamilimu.books.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kamilimu.books.util.BASE_URL
import org.kamilimu.books.viewbooks.data.local.BookDatabase
import org.kamilimu.books.viewbooks.data.remote.BookApiService
import org.kamilimu.books.viewbooks.domain.model.BookData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApiService(okHttpClient: OkHttpClient): BookApiService {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BookApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookDatabase(
        @ApplicationContext appContext: Context
    ): BookDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = BookDatabase::class.java,
            name = "books_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(db: BookDatabase) = db.getBookDao()
}