package com.virtualathlete.myfitness.workout

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.feed.WorkoutViewAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_workout.*
import javax.inject.Inject

@ActivityScoped
class WorkoutFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener, View.OnClickListener {

    private lateinit var workoutAdapter: WorkoutViewAdapter
    private lateinit var viewModel: WorkoutViewModel
    private var switchFeedModeListener: OnSwitchFeedModeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        // Get the ViewModel.
        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
        workoutAdapter = WorkoutViewAdapter()
        workoutAdapter.setOnClickItemListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
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
                switchFeedModeListener?.onSwitchFeedMode()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutSetAdapter = WorkoutSetViewAdapter()
        list_workout_sets_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        list_workout_sets_recycler_view.adapter = workoutSetAdapter
        list_workout_sets_recycler_view.setHasFixedSize(true)
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

    fun setOnSwitchFeedModeListener(activity: Activity) {
        switchFeedModeListener = activity as OnSwitchFeedModeListener
    }

    override fun onDetach() {
        switchFeedModeListener = null
        super.onDetach()
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

    public interface OnSwitchFeedModeListener{
        public fun onSwitchFeedMode()
    }
}