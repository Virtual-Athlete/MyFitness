package com.virtualathlete.myfitness

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_detail_workout.*

/**
 * Created by haris on 2018-01-14.
 */
class WorkoutDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_detail_workout, container, false)
    }

    override fun onResume() {
        super.onResume()
        val workoutAdapter = WorkoutViewAdapter()
        list_exercise_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_exercise_recycler_view.adapter = workoutAdapter
        list_exercise_recycler_view.setHasFixedSize(true)
    }

    override fun onPause() {
        super.onPause()
    }
}