package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PerguntaRepository
import com.example.myapplication.data.repository.ResultadoFinalRepository

class QuizClienteViewModelFactory(
    private val perguntaRepository: PerguntaRepository,
    private val resultadoFinalRepository: ResultadoFinalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizClienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizClienteViewModel(perguntaRepository, resultadoFinalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
