package com.example.myapplication.viewmodel

// ViewModel do quiz do cliente.
// Carrega as perguntas cadastradas, controla a resposta de cada uma
// e salva o resultado na tabela resultado_final quando o jogo acaba.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.ResultadoFinal
import com.example.myapplication.data.repository.PerguntaRepository
import com.example.myapplication.data.repository.ResultadoFinalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizClienteViewModel(
    private val perguntaRepository: PerguntaRepository,
    private val resultadoFinalRepository: ResultadoFinalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizClienteUiState())
    val uiState: StateFlow<QuizClienteUiState> = _uiState.asStateFlow()

    // evita salvar o mesmo jogo duas vezes se o botão for tocado rápido
    private var salvando = false

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
        val state = _uiState.value
        if (state.indiceAtual < state.perguntas.lastIndex) {
            _uiState.update { it.copy(indiceAtual = it.indiceAtual + 1, respostaEscolhida = null) }
            return
        }
        // última pergunta: salva o jogo e só depois avisa a tela que acabou
        if (salvando) return
        salvando = true
        val resultado = ResultadoFinal(
            acertos = state.acertos,
            totalPerguntas = state.perguntas.size,
            dataHora = System.currentTimeMillis()
        )
        viewModelScope.launch {
            resultadoFinalRepository.inserir(resultado)
            _uiState.update { it.copy(finalizado = true) }
        }
    }

}
