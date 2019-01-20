package com.virtualathlete.myfitness.workout

import com.virtualathlete.myfitness.di.FragmentScoped
import com.virtualathlete.myfitness.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class WorkoutModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun workoutFragment(): WorkoutFragment
}
