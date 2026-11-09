package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val CorAdm = Color(0xFFC62828)
val CorCliente = Color(0xFF1565C0)

@Composable
fun CampoTexto(
    titulo: String,
    valor: String,
    placeholder: String,
    aoAlterar: (String) -> Unit,
    corTema: Color,
    ehSenha: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(titulo, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = corTema)
        Spacer(modifier = Modifier.padding(2.dp))
        TextField(
            value = valor,
            onValueChange = aoAlterar,
            placeholder = { Text(placeholder, fontSize = 14.sp, color = Color(0xFF9AA5A1)) },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            visualTransformation = if (ehSenha) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF8FAF9),
                focusedIndicatorColor = corTema,
                unfocusedIndicatorColor = Color(0xFFE2E8F0),
                unfocusedContainerColor = Color(0xFFF8FAF9),
                disabledContainerColor = Color(0xFFF8FAF9),
                cursorColor = corTema
            )
        )
    }
}

@Composable
fun TituloSecao(texto: String) {
    Text(texto, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1F2937))
}

@Composable
fun CaixaErro(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFDECEC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            texto,
            color = Color(0xFFC62828),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CaixaSucesso(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE6F4EC), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            texto,
            color = Color(0xFF00703C),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
