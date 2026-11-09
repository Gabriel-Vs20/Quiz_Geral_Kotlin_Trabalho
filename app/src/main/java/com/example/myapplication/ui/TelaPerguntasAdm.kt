package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.PerguntaRepository
import com.example.myapplication.viewmodel.PerguntasViewModel
import com.example.myapplication.viewmodel.PerguntasViewModelFactory

@Composable
fun TelaPerguntasAdm(
    navController: NavHostController,
    viewModel: PerguntasViewModel = viewModel(
        factory = PerguntasViewModelFactory(
            PerguntaRepository(AppDatabase.getDatabase(LocalContext.current).perguntaDAO())
        )
    )
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val coresDoCampo = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF009739),
        focusedLabelColor = Color(0xFF009739),
        cursorColor = Color(0xFF009739)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F7F6)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    TituloSecao("Cadastro de perguntas")
                    Text(
                        "${uiState.quantidadeCadastrada} cadastradas",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7A74)
                    )
                }
                TextButton(
                    onClick = {
                        navController.navigate(Rotas.LOGIN_ADM) {
                            popUpTo(Rotas.PERGUNTAS_ADM) { inclusive = true }
                        }
                    }
                ) {
                    Text("Sair", color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.enunciado,
                    onValueChange = { viewModel.onEnunciadoChange(it) },
                    label = { Text("Enunciado") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = coresDoCampo
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    "Preencha as alternativas e marque a correta",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6B7A74)
                )
                Spacer(modifier = Modifier.padding(2.dp))

                uiState.alternativas.forEachIndexed { indice, alternativa ->
                    val letra = 'A' + indice
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = uiState.indiceCorreta == indice,
                            onClick = { viewModel.onCorretaChange(indice) },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF009739)),
                            modifier = Modifier.semantics {
                                contentDescription = "Marcar alternativa $letra como correta"
                            }
                        )
                        OutlinedTextField(
                            value = alternativa,
                            onValueChange = { viewModel.onAlternativaChange(indice, it) },
                            label = { Text("Alternativa $letra") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = coresDoCampo
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                }

                if (uiState.erro.isNotBlank()) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    CaixaErro(uiState.erro)
                }

                if (uiState.sucesso.isNotBlank()) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    CaixaSucesso(uiState.sucesso)
                }

                Spacer(modifier = Modifier.padding(6.dp))

                Button(
                    onClick = { viewModel.onSalvar() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009739)),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) { Text("Salvar", fontWeight = FontWeight.ExtraBold) }
            }

        }
    }

}
