package com.example.suantony.suantony_takehome.utils.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suantony.suantony_takehome.data.source.UserDataRepository
import com.example.suantony.suantony_takehome.di.Injection
import com.example.suantony.suantony_takehome.ui.main.UserViewModel

class ViewModelFactory private constructor(private val userDataRepository: UserDataRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                return UserViewModel(userDataRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}