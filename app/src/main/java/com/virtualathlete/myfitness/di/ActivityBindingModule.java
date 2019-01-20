package com.virtualathlete.myfitness.di;

import com.virtualathlete.myfitness.MainActivity;
import com.virtualathlete.myfitness.complete.CompleteActivity;
import com.virtualathlete.myfitness.feed.FeedModule;
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity;
import com.virtualathlete.myfitness.workout.WorkoutModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by haris on 2018-01-11.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {FeedModule.class, WorkoutModule.class})
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {})
    abstract WorkoutDetailActivity workoutDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {})
    abstract CompleteActivity completeActivity();
}
