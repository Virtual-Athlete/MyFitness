package com.virtualathlete.myfitness.feed

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.transition.Transition
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutType
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import java.util.*
import javax.inject.Inject

/**
 * Created by haris on 2018-01-11.
 */

@ActivityScoped
class FeedFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener, View.OnClickListener {
    private lateinit var workoutAdapter: WorkoutViewAdapter
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move);
        }

        setHasOptionsMenu(true)

        // Get the ViewModel.
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        workoutAdapter = WorkoutViewAdapter()
        workoutAdapter.setOnClickItemListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.workout_navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_favorite -> {
                //switchFeedModeListener?.onSwitchFeedMode()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_workout_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_recycler_view.setHasFixedSize(true)
        workout_floating_action_button.setOnClickListener(this)
        sleep_floating_action_button.setOnClickListener(this)
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getWorkouts().observe(this, Observer { workouts ->
                workouts?.let { workoutAdapter.swapWorkouts(it) }
                swipe_refresh_layout.isRefreshing = false
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWorkouts().observe(this, Observer { workouts ->
            workouts?.let {
                workoutAdapter.swapWorkouts(it)
                list_workout_recycler_view.adapter = workoutAdapter
            }
        })
    }

    override fun onClickItem(view: View) {
        val fade: Transition = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        activity!!.window.exitTransition = fade
        val intent = Intent(activity, WorkoutDetailActivity::class.java)

        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    override fun onClick(view: View?) {
        when(view?.id){
            workout_floating_action_button.id -> {
                val workout = Workout("Run", Date().time, WorkoutType.METABOLIC)
                viewModel.addWorkout(workout)
            }
            sleep_floating_action_button.id -> {
                val workout = Workout("Rest", Date().time, WorkoutType.REST)
                viewModel.addWorkout(workout)
            }
        }
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}