package com.virtualathlete.myfitness.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.WorkoutViewAdapter
import com.virtualathlete.myfitness.di.ActivityScoped
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

/**
 * Created by haris on 2018-01-11.
 */

@ActivityScoped
class FeedFragment @Inject constructor() : DaggerFragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() {
        super.onResume()
        val workoutAdapter = WorkoutViewAdapter()
        list_workout_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_recycler_view.adapter = workoutAdapter
        list_workout_recycler_view.setHasFixedSize(true)
    }

    override fun onPause() {
        super.onPause()
    }
}