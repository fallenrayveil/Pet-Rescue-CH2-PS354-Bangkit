package com.example.petrescuecapstone.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petrescuecapstone.data.Injection
import com.example.petrescuecapstone.repository.UserRepository

class ViewModelFactory(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                    SignInViewModel(repository) as T
                }
                modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                    SignUpViewModel(repository) as T
                }
                modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                    ProfileViewModel(repository) as T
                }

                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }

        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null
            @JvmStatic
            fun getInstance(context: Context): ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }
    }