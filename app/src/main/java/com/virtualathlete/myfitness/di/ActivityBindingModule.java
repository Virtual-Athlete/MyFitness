package com.virtualathlete.myfitness.di;

import com.virtualathlete.myfitness.MainActivity;
import com.virtualathlete.myfitness.complete.CompleteActivity;
import com.virtualathlete.myfitness.feed.FeedModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by haris on 2018-01-11.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {FeedModule.class})
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {})
    abstract CompleteActivity completeActivity();
}
