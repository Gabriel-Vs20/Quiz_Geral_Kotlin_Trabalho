package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PerguntaDAO {

    @Insert
    suspend fun inserir(pergunta: Pergunta)

    @Query("SELECT COUNT(*) FROM perguntas")
    fun contar(): Flow<Int>

    @Query("SELECT * FROM perguntas")
    suspend fun listarTodas(): List<Pergunta>

}
