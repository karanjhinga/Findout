package com.karan.findout.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: FindoutViewModelFactory): ViewModelProvider.Factory
}

class FindoutViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
        val creator =
            found?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return creator.get() as T
    }
}