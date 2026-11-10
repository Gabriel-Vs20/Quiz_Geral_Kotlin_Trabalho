package com.example.myapplication.data.repository

// Repository - só ele fala com o DAO dos resultados.

import com.example.myapplication.data.local.ResultadoFinal
import com.example.myapplication.data.local.ResultadoFinalDAO

class ResultadoFinalRepository(private val resultadoFinalDao: ResultadoFinalDAO) {

    suspend fun inserir(resultado: ResultadoFinal) {
        resultadoFinalDao.inserir(resultado)
    }

}
