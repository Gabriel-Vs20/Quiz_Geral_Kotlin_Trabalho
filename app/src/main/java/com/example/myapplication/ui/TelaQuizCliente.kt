package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.PerguntaRepository
import com.example.myapplication.data.repository.ResultadoFinalRepository
import com.example.myapplication.viewmodel.QuizClienteViewModel
import com.example.myapplication.viewmodel.QuizClienteViewModelFactory

@Composable
fun TelaQuizCliente(
    viewModel: QuizClienteViewModel = viewModel(
        factory = QuizClienteViewModelFactory(
            PerguntaRepository(AppDatabase.getDatabase(LocalContext.current).perguntaDAO()),
            ResultadoFinalRepository(AppDatabase.getDatabase(LocalContext.current).resultadoFinalDAO())
        )
    )
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pergunta = uiState.perguntaAtual

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F7F6)
    ) {
        // quando acabam as perguntas a tela fica vazia por enquanto
        if (pergunta == null) return@Surface

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                "Pergunta ${uiState.indiceAtual + 1} de ${uiState.perguntas.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7A74)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            TituloSecao(pergunta.enunciado)
            Spacer(modifier = Modifier.padding(8.dp))

            val alternativas = listOf(
                pergunta.alternativaA,
                pergunta.alternativaB,
                pergunta.alternativaC,
                pergunta.alternativaD
            )
            val respondida = uiState.respostaEscolhida != null

            alternativas.forEachIndexed { indice, alternativa ->
                val letra = 'A' + indice
                val corBorda = when {
                    !respondida -> Color(0xFFE2E8F0)
                    indice == pergunta.indiceCorreta -> Color(0xFF00703C)
                    indice == uiState.respostaEscolhida -> Color(0xFFC62828)
                    else -> Color(0xFFE2E8F0)
                }
                Text(
                    "$letra) $alternativa",
                    fontSize = 15.sp,
                    color = Color(0xFF1F2937),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(2.dp, corBorda, RoundedCornerShape(10.dp))
                        .clickable(enabled = !respondida) { viewModel.onResponder(indice) }
                        .padding(14.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }

            if (respondida) {
                Spacer(modifier = Modifier.padding(4.dp))
                if (uiState.respostaEscolhida == pergunta.indiceCorreta) {
                    CaixaSucesso("Resposta certa!")
                } else {
                    CaixaErro("Resposta errada! A certa é a ${'A' + pergunta.indiceCorreta}")
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Button(
                    onClick = { viewModel.onProxima() },
                    colors = ButtonDefaults.buttonColors(containerColor = CorCliente),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) { Text("Próxima", fontWeight = FontWeight.ExtraBold) }
            }

        }
    }

}
