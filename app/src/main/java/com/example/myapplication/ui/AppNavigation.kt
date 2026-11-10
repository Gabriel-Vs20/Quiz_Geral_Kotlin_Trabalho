package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.LOGIN_ADM
    ) {

        composable(Rotas.LOGIN_ADM) {
            TelaLoginAdm(navController)
        }

        composable(Rotas.PERGUNTAS_ADM) {
            TelaPerguntasAdm(navController)
        }

        composable(Rotas.QUIZ_CLIENTE) {
            TelaQuizCliente()
        }

    }

}
