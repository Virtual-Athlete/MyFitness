package com.virtualathlete.myfitness

import android.app.Application
import com.virtualathlete.myfitness.di.AppComponent
import com.virtualathlete.myfitness.di.DaggerAppComponent
import dagger.android.DaggerApplication
import dagger.android.AndroidInjector



/**
 * Created by haris on 2018-01-11.
 */
class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().application(this).build();
    }
}