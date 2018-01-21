package com.virtualathlete.myfitness.feed

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutType
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by haris on 2018-01-11.
 */

@ActivityScoped
class FeedFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener, View.OnClickListener {
    private lateinit var databaseRef: DatabaseReference
    private lateinit var workoutAdapter: WorkoutViewAdapter
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseRef = FirebaseDatabase.getInstance().reference

        workoutAdapter = WorkoutViewAdapter()
        workoutAdapter.setOnClickItemListener(this)

        // Get the ViewModel.
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        viewModel.getWorkouts().observe(this, Observer { workouts ->
            workouts?.let { workoutAdapter.swapWorkouts(it) }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_workout_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_recycler_view.adapter = workoutAdapter
        list_workout_recycler_view.setHasFixedSize(true)
        workout_floating_action_button.setOnClickListener(this)
        sleep_floating_action_button.setOnClickListener(this)
        swipe_refresh_layout.setOnRefreshListener({
            viewModel.getWorkouts().observe(this, Observer { workouts ->
                workouts?.let { workoutAdapter.swapWorkouts(it) }
                swipe_refresh_layout.isRefreshing = false
            })
        })
    }

    override fun onClickItem(view: View) {
        val fade: Transition = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        activity.window.exitTransition = fade
        val intent = Intent(activity, WorkoutDetailActivity::class.java)

        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    override fun onClick(view: View?) {
        when(view?.id){
            workout_floating_action_button.id -> {
                val workout = Workout("Run", SimpleDateFormat("yyyy-MM-dd").format(Date()), WorkoutType.METABOLIC)
                viewModel.addWorkout(workout)
            }
            sleep_floating_action_button.id -> {
                val workout = Workout("Rest", SimpleDateFormat("yyyy-MM-dd").format(Date()), WorkoutType.REST)
                viewModel.addWorkout(workout)
            }
        }
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}