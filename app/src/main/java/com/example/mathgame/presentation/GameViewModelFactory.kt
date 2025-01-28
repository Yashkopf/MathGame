package com.example.mathgame.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Provides
import java.util.ServiceLoader.Provider
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class GameViewModelFactory<T : ViewModel>(
    private val kClass: KClass<T>,
    private val creator: () -> T
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(kClass.java)) return creator() as T
        throw IllegalArgumentException("Unknown class name")
    }

}


//class GameViewModelFactory @Inject constructor(
//private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
//) : ViewModelProvider.Factory {
//
//    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val provider = viewModelMap[modelClass]
//            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
//        @Suppress("UNCHECKED_CAST")
//        return provider.get() as T
//    }
//}
