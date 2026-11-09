package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pergunta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun perguntaDAO(): PerguntaDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val temInstance = INSTANCE

            if (temInstance != null) {
                return temInstance
            } else {

                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "quiz_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }

            }

        }

    }

}
