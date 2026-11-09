package com.example.myapplication.viewmodel

// ViewModel do cadastro de perguntas.
// Só ele fala com o Repository; a tela só lê o uiState e chama as funções "on..."

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Pergunta
import com.example.myapplication.data.repository.PerguntaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerguntasViewModel(
    private val perguntaRepository: PerguntaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerguntasUiState())
    val uiState: StateFlow<PerguntasUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            perguntaRepository.contar().collect { quantidade ->
                _uiState.update { it.copy(quantidadeCadastrada = quantidade) }
            }
        }
    }

    fun onEnunciadoChange(valor: String) {
        _uiState.update { it.copy(enunciado = valor, sucesso = "") }
    }

    fun onAlternativaChange(indice: Int, valor: String) {
        _uiState.update { state ->
            state.copy(
                alternativas = state.alternativas.mapIndexed { i, atual ->
                    if (i == indice) valor else atual
                },
                sucesso = ""
            )
        }
    }

    fun onCorretaChange(indice: Int) {
        _uiState.update { it.copy(indiceCorreta = indice, sucesso = "") }
    }

    fun onSalvar() {
        val state = _uiState.value
        val enunciado = state.enunciado.trim()
        val alternativas = state.alternativas.map { it.trim() }
        val indiceCorreta = state.indiceCorreta

        val erro = when {
            enunciado.isBlank() -> "Informe o enunciado da pergunta"
            alternativas.any { it.isBlank() } -> "Preencha as 4 alternativas"
            alternativas.distinctBy { it.lowercase() }.size < alternativas.size ->
                "As alternativas não podem ser iguais"
            indiceCorreta == null -> "Marque qual alternativa é a correta"
            else -> ""
        }
        if (erro.isNotBlank() || indiceCorreta == null) {
            _uiState.update { it.copy(erro = erro, sucesso = "") }
            return
        }

        val pergunta = Pergunta(
            enunciado = enunciado,
            alternativaA = alternativas[0],
            alternativaB = alternativas[1],
            alternativaC = alternativas[2],
            alternativaD = alternativas[3],
            indiceCorreta = indiceCorreta
        )

        viewModelScope.launch {
            perguntaRepository.inserir(pergunta)
        }

        _uiState.update {
            it.copy(
                enunciado = "",
                alternativas = List(4) { "" },
                indiceCorreta = null,
                erro = "",
                sucesso = "Pergunta cadastrada!"
            )
        }
    }

}
