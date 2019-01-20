package com.virtualathlete.myfitness

import com.google.firebase.database.FirebaseDatabase
import com.virtualathlete.myfitness.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/**
 * Created by haris on 2018-01-11.
 */
class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().application(this).build()
    }
}