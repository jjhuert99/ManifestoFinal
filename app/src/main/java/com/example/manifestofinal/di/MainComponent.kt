package com.example.manifestofinal.di

import android.content.Context
import androidx.room.Room
import com.example.manifestofinal.database.GuestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainComponent {

    @Singleton
    @Provides
    fun provideGuestList(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        GuestDatabase::class.java,
        "guest_database_database"
    ).build()

    @Singleton
    @Provides
    fun provideGuestDao(db: GuestDatabase) = db.guestDao
}
