package com.example.manifestofinal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Guest::class], version = 1, exportSchema = false)
abstract class GuestDatabase: RoomDatabase() {
    abstract val guestDao: GuestDao

    companion object{
        @Volatile
        private var INSTANCE: GuestDatabase? = null

        fun getInstance(context: Context): GuestDatabase{
            synchronized (this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GuestDatabase::class.java,
                        "guest_database_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
