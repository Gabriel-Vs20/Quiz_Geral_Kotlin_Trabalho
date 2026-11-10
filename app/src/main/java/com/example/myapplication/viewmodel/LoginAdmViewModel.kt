package com.example.myapplication.viewmodel

// ViewModel do login do administrador e do cliente.
// Os logins são fixos e locais: as senhas estão dentro do APK, então isso só separa as telas.

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginAdmUiState(
    val usuario: String = "",
    val senha: String = "",
    val erro: String = "",
    val sucesso: String = "",
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
        _uiState.update {
            it.copy(usuario = "", senha = "", erro = "", sucesso = "", modoAdm = !it.modoAdm)
        }
    }

    // devolve true quando pode entrar na área escolhida (administrador ou cliente)
    fun onEntrar(): Boolean {
        val state = _uiState.value
        val usuario = state.usuario.trim()
        val loginCerto = if (state.modoAdm) {
            usuario == USUARIO_ADM && state.senha == SENHA_ADM
        } else {
            usuario == USUARIO_CLIENTE && state.senha == SENHA_CLIENTE
        }
        if (loginCerto) {
            return true
        }
        _uiState.update { it.copy(erro = "Usuário ou senha inválidos") }
        return false
    }

    private companion object {
        const val USUARIO_ADM = "admin"
        const val SENHA_ADM = "admin123"
        const val USUARIO_CLIENTE = "cliente"
        const val SENHA_CLIENTE = "cliente123"
    }

}
