package com.karan.findout.di

import com.karan.findout.FindoutApplication
import com.karan.findout.di.modules.ActivityModule
import com.karan.findout.di.modules.AppModule
import com.karan.findout.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<FindoutApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FindoutApplication): Builder

        fun build(): AppComponent
    }
}
