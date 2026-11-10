package com.example.myapplication.data.local

// Entity do Room - resultado de cada jogo do cliente
// dataHora é o momento em que o jogo terminou (System.currentTimeMillis)

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultado_final")
data class ResultadoFinal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val acertos: Int,
    val totalPerguntas: Int,
    val dataHora: Long
)
