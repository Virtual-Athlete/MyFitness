package com.virtualathlete.myfitness

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import com.virtualathlete.myfitness.feed.FeedFragment
import com.virtualathlete.myfitness.workout.WorkoutFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity @Inject constructor() : DaggerAppCompatActivity(), WorkoutFragment.OnSwitchFeedModeListener {

    @Inject lateinit var feedFragment: FeedFragment
    @Inject lateinit var workoutFragment: WorkoutFragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, workoutFragment).commit()
                workoutFragment.setOnSwitchFeedModeListener(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onSwitchFeedMode(appBarLayout: AppBarLayout) {
        supportFragmentManager.beginTransaction()
                .addSharedElement(appBarLayout, ViewCompat.getTransitionName(appBarLayout)!!)
                .addToBackStack(feedFragment.javaClass.simpleName)
                .replace(R.id.fragment_frame_layout, feedFragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, workoutFragment).commit()
        workoutFragment.setOnSwitchFeedModeListener(this)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
