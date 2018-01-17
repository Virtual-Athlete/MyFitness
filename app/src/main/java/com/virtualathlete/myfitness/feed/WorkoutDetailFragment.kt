package com.virtualathlete.myfitness.feed

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.complete.CompleteActivity
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
        val workoutSetAdapter = WorkoutSetViewAdapter()
        list_workout_sets_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_sets_recycler_view.adapter = workoutSetAdapter
        list_workout_sets_recycler_view.setHasFixedSize(true)

        floating_action_button.setOnClickListener({ view ->
            onFabClicked(view)
        })
    }

    override fun onPause() {
        super.onPause()
    }

    private fun onFabClicked(view: View){
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "reveal");

        val revealX = (view.left + view.right) / 2
        val revealY = (view.top + view.bottom) / 2

        val intent = Intent(activity, CompleteActivity::class.java)
        intent.putExtra(CompleteActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CompleteActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }


}