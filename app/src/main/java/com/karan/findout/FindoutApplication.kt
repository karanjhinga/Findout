package com.karan.findout

import com.karan.findout.di.DaggerAppComponent
import dagger.android.DaggerApplication

class FindoutApplication : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()
}