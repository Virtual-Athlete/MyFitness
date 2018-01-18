package com.virtualathlete.myfitness.feed

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Explode
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

/**
 * Created by haris on 2018-01-11.
 */

@ActivityScoped
class FeedFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener {
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

    override fun onClickItem(view: View) {
        val explode: Transition = Explode()
        //explode.excludeTarget(android.R.id.statusBarBackground, true)
        //explode.excludeTarget(android.R.id.navigationBarBackground, true)
        activity.window.exitTransition = explode
        //window.allowEnterTransitionOverlap = true
        //activity.window.allowReturnTransitionOverlap = true
        val intent = Intent(activity, WorkoutDetailActivity::class.java)

        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}