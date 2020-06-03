package com.karan.findout.di.modules

import android.content.Context
import com.karan.findout.FindoutApplication
import com.karan.findout.data.db.DatabaseModule
import com.karan.findout.data.network.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class AppModule {

    @Provides
    fun provideContext(application: FindoutApplication): Context {
        return application.applicationContext
    }
}