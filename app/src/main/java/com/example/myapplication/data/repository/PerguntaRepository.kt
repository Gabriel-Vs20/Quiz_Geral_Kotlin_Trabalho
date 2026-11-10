package com.example.myapplication.data.repository

// Repository - só ele fala com o DAO das perguntas.

import com.example.myapplication.data.local.Pergunta
import com.example.myapplication.data.local.PerguntaDAO
import kotlinx.coroutines.flow.Flow

class PerguntaRepository(private val perguntaDao: PerguntaDAO) {

    fun contar(): Flow<Int> = perguntaDao.contar()

    suspend fun listarTodas(): List<Pergunta> = perguntaDao.listarTodas()

    suspend fun inserir(pergunta: Pergunta) {
        perguntaDao.inserir(pergunta)
    }

}
