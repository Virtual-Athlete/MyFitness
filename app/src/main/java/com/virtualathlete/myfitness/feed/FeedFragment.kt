package com.virtualathlete.myfitness.feed

import android.app.ActivityOptions
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
import com.virtualathlete.myfitness.R
import com.virtualathlete.myfitness.di.ActivityScoped
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutType
import com.virtualathlete.myfitness.workout.WorkoutDetailActivity
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import java.util.*
import javax.inject.Inject
import android.content.ContentValues.TAG
import com.google.firebase.database.*
import android.widget.Toast
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


/**
 * Created by haris on 2018-01-11.
 */

@ActivityScoped
class FeedFragment @Inject constructor() : DaggerFragment(), WorkoutViewAdapter.OnClickItemListener {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var workoutAdapter: WorkoutViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseRef = FirebaseDatabase.getInstance().reference

        workoutAdapter = WorkoutViewAdapter()
        workoutAdapter.setOnClickItemListener(this)

        var workout: Workout = Workout("Upper body", SimpleDateFormat("yyyy-MM-dd").format(Date()), WorkoutType.WEIGHTLIFTING)
        var workout2: Workout = Workout("Lower body", SimpleDateFormat("yyyy-MM-dd").format(Date()), WorkoutType.WEIGHTLIFTING)
        var workout3: Workout = Workout("Upper body", SimpleDateFormat("yyyy-MM-dd").format(Date()), WorkoutType.WEIGHTLIFTING)

        /*databaseRef.child("workouts").push().setValue(workout)
        databaseRef.child("workouts").push().setValue(workout2)
        databaseRef.child("workouts").push().setValue(workout3)*/

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var workouts: MutableList<Workout> = ArrayList<Workout>()
                for (workoutSnapshot: DataSnapshot in dataSnapshot.children){
                    val workout: Workout? = workoutSnapshot.getValue<Workout>(Workout::class.java)
                    workout?.let { workouts.add(it) }
                }
                workoutAdapter.swapWorkouts(workouts)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                val newComment = dataSnapshot.getValue<Workout>(Workout::class.java)
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                val movedComment = dataSnapshot.getValue<Workout>(Workout::class.java)
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
                Toast.makeText(activity, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show()
            }
        }
        databaseRef.addChildEventListener(childEventListener)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_workout_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list_workout_recycler_view.adapter = workoutAdapter
        list_workout_recycler_view.setHasFixedSize(true)
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

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}