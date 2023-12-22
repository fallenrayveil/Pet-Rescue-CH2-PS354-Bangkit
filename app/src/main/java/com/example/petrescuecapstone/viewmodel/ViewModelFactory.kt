package com.example.petrescuecapstone.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petrescuecapstone.data.Injection
import com.example.petrescuecapstone.repository.LostFoundRepository
import com.example.petrescuecapstone.repository.MyPetRepository
import com.example.petrescuecapstone.repository.PredictRepository
import com.example.petrescuecapstone.repository.UserRepository

class ViewModelFactory(
    private val repository: UserRepository,
    private val predictRepository: PredictRepository,
    private val lostFoundRepository: LostFoundRepository,
    private val myPetRepository: MyPetRepository
) :
    ViewModelProvider.NewInstanceFactory() {
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

            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(predictRepository) as T
            }
            modelClass.isAssignableFrom(LostFoundViewModel::class.java) -> {
                LostFoundViewModel(lostFoundRepository) as T
            }
            modelClass.isAssignableFrom(DetailPetUserViewModel::class.java) -> {
                DetailPetUserViewModel(myPetRepository) as T
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
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.predictRepository(context),
                        Injection.lostfoundRepository(context),
                        Injection.mypetRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}