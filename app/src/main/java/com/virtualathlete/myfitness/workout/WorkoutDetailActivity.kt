package com.virtualathlete.myfitness.workout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.complete.CompleteActivity
import com.virtualathlete.myfitness.model.WorkoutSet
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail_workout.*


/**
 * Created by haris on 2018-01-14.
 */
class WorkoutDetailActivity : DaggerAppCompatActivity() {

    private lateinit var workoutSetAdapter: WorkoutSetViewAdapter
    private lateinit var viewModel: WorkoutSetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_workout)

        viewModel = ViewModelProviders.of(this).get(WorkoutSetViewModel::class.java)
        workoutSetAdapter = WorkoutSetViewAdapter()
        //workoutSetAdapter.setOnClickItemListener(this)
        list_workout_sets_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        list_workout_sets_recycler_view.setHasFixedSize(true)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            app_bar_layout.setExpanded(false, true)
        }
        floating_action_button.setOnClickListener({ view ->
            onFabClicked(view)
        })


        //viewModel.addWorkoutSet(WorkoutSet("Workout A", 4, 80, "-L4IT6XVgGi5_S1SpX_K"))
        //viewModel.addWorkoutSet(WorkoutSet("Workout B", 5, 60, "-L4IT6XVgGi5_S1SpX_K"))
        //viewModel.addWorkoutSet(WorkoutSet("Workout C", 2, 60, "-L4IT6XVgGi5_S1SpX_K"))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWorkoutSets(intent.getStringExtra("workout_id")).observe(this, Observer { workoutSets ->
            workoutSets?.let {
                workoutSetAdapter.swapWorkoutSets(it)
                list_workout_sets_recycler_view.adapter = workoutSetAdapter
            }
        })
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