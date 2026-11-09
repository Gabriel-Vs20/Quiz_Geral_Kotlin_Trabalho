package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.LoginAdmViewModel

@Composable
fun TelaLoginAdm(
    navController: NavHostController,
    viewModel: LoginAdmViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.size(70.dp).background(Color(0xFF009739), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                "Quiz Geral",
                color = Color(0xFF009739),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )
            Text(
                if (uiState.modoAdm) "ÁREA DO ADMINISTRADOR" else "ÁREA DO USUÁRIO",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7A74)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            CampoTexto(
                titulo = "USUÁRIO",
                valor = uiState.usuario,
                placeholder = "Digite o usuário",
                aoAlterar = { viewModel.onUsuarioChange(it) }
            )

            Spacer(modifier = Modifier.padding(7.dp))

            CampoTexto(
                titulo = "SENHA",
                valor = uiState.senha,
                placeholder = "Digite a senha",
                aoAlterar = { viewModel.onSenhaChange(it) },
                ehSenha = true
            )

            if (uiState.erro.isNotBlank()) {
                Spacer(modifier = Modifier.padding(5.dp))
                CaixaErro(uiState.erro)
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Button(
                onClick = {
                    if (viewModel.onEntrar()) {
                        navController.navigate(Rotas.PERGUNTAS_ADM) {
                            popUpTo(Rotas.LOGIN_ADM) { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009739)),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Entrar", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp) }

            Spacer(modifier = Modifier.padding(6.dp))

            OutlinedButton(
                onClick = { viewModel.onAlternarModo() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF009739)),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (uiState.modoAdm) "Trocar para usuário" else "Trocar para administrador",
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}
