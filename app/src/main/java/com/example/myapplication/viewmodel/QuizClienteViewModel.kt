package com.example.myapplication.viewmodel

// ViewModel do quiz do cliente.
// Carrega as perguntas cadastradas e controla a resposta de cada uma.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PerguntaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizClienteViewModel(
    private val perguntaRepository: PerguntaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizClienteUiState())
    val uiState: StateFlow<QuizClienteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val perguntas = perguntaRepository.listarTodas()
            _uiState.update { it.copy(carregado = true, perguntas = perguntas) }
        }
    }

    fun onResponder(indice: Int) {
        val state = _uiState.value
        val pergunta = state.perguntaAtual ?: return
        if (state.respostaEscolhida != null) return
        _uiState.update {
            it.copy(
                respostaEscolhida = indice,
                acertos = if (indice == pergunta.indiceCorreta) it.acertos + 1 else it.acertos
            )
        }
    }

    fun onProxima() {
        _uiState.update { it.copy(indiceAtual = it.indiceAtual + 1, respostaEscolhida = null) }
    }

}
