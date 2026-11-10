package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ResultadoFinalDAO {

    @Insert
    suspend fun inserir(resultado: ResultadoFinal)

}
