package com.virtualathlete.myfitness.di;

import com.virtualathlete.myfitness.MainActivity;
import com.virtualathlete.myfitness.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by haris on 2018-01-11.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity mainActivity();
}
