package com.example.myapplication.data.local

// Entity do Room - pergunta do quiz com 4 alternativas
// indiceCorreta vai de 0 (alternativa A) até 3 (alternativa D)

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perguntas")
data class Pergunta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val enunciado: String,
    val alternativaA: String,
    val alternativaB: String,
    val alternativaC: String,
    val alternativaD: String,
    val indiceCorreta: Int
)
