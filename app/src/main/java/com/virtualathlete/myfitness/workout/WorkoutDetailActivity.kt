package com.virtualathlete.myfitness.workout

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.complete.CompleteActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail_workout.*


/**
 * Created by haris on 2018-01-14.
 */
class WorkoutDetailActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_workout)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            app_bar_layout.setExpanded(false, true)
        }
        floating_action_button.setOnClickListener { view ->
            onFabClicked(view)
        }
    }

    override fun onResume() {
        super.onResume()
        val workoutSetAdapter = WorkoutSetViewAdapter()
        list_workout_sets_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        list_workout_sets_recycler_view.adapter = workoutSetAdapter
        list_workout_sets_recycler_view.setHasFixedSize(true)
    }

    override fun onPause() {
        super.onPause()
    }

    private fun onFabClicked(view: View){
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");

        val revealX = (view.left + view.right) / 2
        val revealY = (view.top + view.bottom) / 2

        val intent = Intent(this, CompleteActivity::class.java)
        intent.putExtra(CompleteActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CompleteActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}