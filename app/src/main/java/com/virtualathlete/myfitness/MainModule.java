package com.virtualathlete.myfitness;

import com.virtualathlete.myfitness.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by haris on 2018-01-11.
 */

@Module
public abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract FeedFragment feedFragment();
}
