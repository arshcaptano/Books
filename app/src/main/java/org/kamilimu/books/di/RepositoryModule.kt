package org.kamilimu.books.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kamilimu.books.bookmarks.data.repository.BookmarkRepositoryImpl
import org.kamilimu.books.bookmarks.domain.repository.BookmarkRepository
import org.kamilimu.books.viewbooks.data.repository.BookRepositoryImpl
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    @Singleton
    abstract fun provideBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository

}