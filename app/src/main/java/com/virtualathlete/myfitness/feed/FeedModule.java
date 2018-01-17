package com.virtualathlete.myfitness.feed;

import com.virtualathlete.myfitness.di.FragmentScoped;
import com.virtualathlete.myfitness.feed.FeedFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by haris on 2018-01-11.
 */

@Module
public abstract class FeedModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract FeedFragment feedFragment();
}
