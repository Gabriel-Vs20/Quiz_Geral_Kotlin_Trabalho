package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaResultadoCliente(
    navController: NavHostController,
    acertos: Int,
    total: Int
) {

    val porcentagem = if (total > 0) acertos * 100 / total else 0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "FIM DE JOGO",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7A74)
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                "$acertos de $total",
                color = CorCliente,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp
            )
            Text(
                "acertos ($porcentagem%)",
                fontSize = 16.sp,
                color = Color(0xFF1F2937)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = {
                    navController.navigate(Rotas.QUIZ_CLIENTE) {
                        popUpTo(Rotas.RESULTADO_CLIENTE) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = CorCliente),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Jogar de novo", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp) }

            Spacer(modifier = Modifier.padding(6.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Rotas.LOGIN_ADM) {
                        popUpTo(Rotas.RESULTADO_CLIENTE) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CorCliente),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Sair", fontWeight = FontWeight.Bold) }

        }
    }

}
