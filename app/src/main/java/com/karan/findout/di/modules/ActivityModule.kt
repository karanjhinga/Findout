package com.karan.findout.di.modules

import com.karan.findout.di.scopes.ActivityScoped
import com.karan.findout.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contibuteMainActivity(): MainActivity
}
