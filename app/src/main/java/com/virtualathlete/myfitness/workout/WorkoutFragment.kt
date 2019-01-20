package com.virtualathlete.myfitness.workout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.feed.WorkoutViewAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class WorkoutFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener, View.OnClickListener {
    //private lateinit var databaseRef: DatabaseReference
    private lateinit var workoutAdapter: WorkoutViewAdapter
    private lateinit var viewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //databaseRef = FirebaseDatabase.getInstance().reference

        // Get the ViewModel.
        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
        workoutAdapter = WorkoutViewAdapter()
        workoutAdapter.setOnClickItemListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*list_workout_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_recycler_view.setHasFixedSize(true)
        workout_floating_action_button.setOnClickListener(this)
        sleep_floating_action_button.setOnClickListener(this)
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getWorkouts().observe(this, Observer { workouts ->
                workouts?.let { workoutAdapter.swapWorkouts(it) }
                swipe_refresh_layout.isRefreshing = false
            })
        }*/
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWorkout().observe(this, Observer { workouts ->
            workouts?.let {
                //workoutAdapter.swapWorkouts(it)
                ///list_workout_recycler_view.adapter = workoutAdapter
            }
        })
    }

    override fun onClickItem(view: View) {
        /*val fade: Transition = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        activity!!.window.exitTransition = fade
        val intent = Intent(activity, WorkoutDetailActivity::class.java)

        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());*/
    }

    override fun onClick(view: View?) {
        /*when(view?.id){
            workout_floating_action_button.id -> {
                val workout = Workout("Run", Date().time, WorkoutType.METABOLIC)
                viewModel.addWorkout(workout)
            }
            sleep_floating_action_button.id -> {
                val workout = Workout("Rest", Date().time, WorkoutType.REST)
                viewModel.addWorkout(workout)
            }
        }*/
    }
}