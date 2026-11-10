package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PerguntaRepository

class QuizClienteViewModelFactory(
    private val perguntaRepository: PerguntaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizClienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizClienteViewModel(perguntaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
