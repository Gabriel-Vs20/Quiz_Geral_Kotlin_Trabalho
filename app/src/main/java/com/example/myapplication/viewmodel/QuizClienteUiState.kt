package com.example.myapplication.viewmodel

// Tudo que a tela do quiz do cliente precisa para se desenhar

import com.example.myapplication.data.local.Pergunta

data class QuizClienteUiState(
    val carregado: Boolean = false,
    val perguntas: List<Pergunta> = emptyList(),
    val indiceAtual: Int = 0,
    val respostaEscolhida: Int? = null,
    val acertos: Int = 0,
    val finalizado: Boolean = false
) {
    val perguntaAtual: Pergunta? get() = perguntas.getOrNull(indiceAtual)
}
