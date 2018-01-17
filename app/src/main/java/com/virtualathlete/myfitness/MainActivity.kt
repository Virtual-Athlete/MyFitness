package com.virtualathlete.myfitness

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.virtualathlete.myfitness.feed.FeedFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity() {

    @Inject lateinit var feedFragment: FeedFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                fragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, feedFragment);
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager.beginTransaction().replace(R.id.fragment_frame_layout, feedFragment).commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
