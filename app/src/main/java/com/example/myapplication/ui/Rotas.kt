package com.example.myapplication.ui

object Rotas {

    const val LOGIN_ADM = "login_adm"
    const val PERGUNTAS_ADM = "perguntas_adm"
    const val QUIZ_CLIENTE = "quiz_cliente"
    const val RESULTADO_CLIENTE = "resultado_cliente/{acertos}/{total}"

    fun resultadoCliente(acertos: Int, total: Int) = "resultado_cliente/$acertos/$total"

}
