package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Pergunta::class, ResultadoFinal::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun perguntaDAO(): PerguntaDAO

    abstract fun resultadoFinalDAO(): ResultadoFinalDAO

    companion object {

        // versão 2 cria a tabela resultado_final sem apagar as perguntas já cadastradas
        private val MIGRACAO_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `resultado_final` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`acertos` INTEGER NOT NULL, " +
                        "`totalPerguntas` INTEGER NOT NULL, " +
                        "`dataHora` INTEGER NOT NULL)"
                )
            }
        }

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
                    ).addMigrations(MIGRACAO_1_2).build()
                    INSTANCE = instance
                    return instance
                }

            }

        }

    }

}
