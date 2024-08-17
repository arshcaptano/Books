package org.kamilimu.books.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kamilimu.books.bookmarks.data.local.BookmarkDatabase
import org.kamilimu.books.util.BASE_URL
import org.kamilimu.books.util.DATABASE_NAME
import org.kamilimu.books.viewbooks.data.remote.BookApiService
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
    fun provideBookmarkDatabase(
        @ApplicationContext appContext: Context
    ): BookmarkDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = BookmarkDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(db: BookmarkDatabase) = db.getBookmarkDao()
}