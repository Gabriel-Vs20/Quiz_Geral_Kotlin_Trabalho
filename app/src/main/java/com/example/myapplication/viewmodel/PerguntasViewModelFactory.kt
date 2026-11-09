package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PerguntaRepository

class PerguntasViewModelFactory(
    private val perguntaRepository: PerguntaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerguntasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerguntasViewModel(perguntaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
