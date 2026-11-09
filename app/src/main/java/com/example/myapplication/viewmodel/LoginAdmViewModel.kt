package com.example.myapplication.viewmodel

// ViewModel do login do administrador.
// O login é fixo e local: a senha está dentro do APK, então isso só separa as telas.

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginAdmUiState(
    val usuario: String = "",
    val senha: String = "",
    val erro: String = "",
    val modoAdm: Boolean = true
)

class LoginAdmViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginAdmUiState())
    val uiState: StateFlow<LoginAdmUiState> = _uiState.asStateFlow()

    fun onUsuarioChange(valor: String) {
        _uiState.update { it.copy(usuario = valor) }
    }

    fun onSenhaChange(valor: String) {
        _uiState.update { it.copy(senha = valor) }
    }

    fun onAlternarModo() {
        _uiState.update { it.copy(usuario = "", senha = "", erro = "", modoAdm = !it.modoAdm) }
    }

    // devolve true quando pode entrar na área do administrador
    fun onEntrar(): Boolean {
        val state = _uiState.value
        if (!state.modoAdm) {
            _uiState.update { it.copy(erro = "A área do usuário ainda não está disponível") }
            return false
        }
        if (state.usuario.trim() == USUARIO_ADM && state.senha == SENHA_ADM) {
            return true
        }
        _uiState.update { it.copy(erro = "Usuário ou senha inválidos") }
        return false
    }

    private companion object {
        const val USUARIO_ADM = "admin"
        const val SENHA_ADM = "admin123"
    }

}
