package com.virtualathlete.myfitness.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualathlete.myfitness.model.Workout


/**
 * Created by haris on 2018-01-20.
 */

class FeedViewModel: ViewModel(){
    var workouts: MutableLiveData<HashMap<String, MutableList<Workout>>> = MutableLiveData()

    fun getWorkouts() : LiveData<HashMap<String, MutableList<Workout>>> {
        if (workouts.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("workouts")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val mutableWorkouts = ArrayList<Workout>()
                                dataSnapshot.children
                                        .map { it.getValue<Workout>(Workout::class.java) }
                                        .forEach { workout -> workout?.let { mutableWorkouts.add(it) } }
                                workouts.postValue(sortWorkoutsByDate(mutableWorkouts))
                            }
                        }
                        override fun onCancelled(p0: DatabaseError?) {
                            // Crash, lets notify fail somehow!
                        }
                    })
        }
        return workouts
    }

    fun addWorkout(workout: Workout){
        FirebaseDatabase.getInstance()
                .getReference("workouts")
                .push()
                .setValue(workout)
    }

    private fun sortWorkoutsByDate(workouts: ArrayList<Workout>) : HashMap<String, MutableList<Workout>>{

        workouts.sortedBy { workout -> workout.date }

        val sortedWorkouts: HashMap<String, MutableList<Workout>> = HashMap()
        workouts.forEach{ workout -> run {
                var workoutsByDate: MutableList<Workout>? = sortedWorkouts[workout.date.format()]
                if (workoutsByDate == null) {
                    workoutsByDate = ArrayList<Workout>()
                    val newDateKey: String = workout.date
                    sortedWorkouts[newDateKey] = workoutsByDate
                }
            workoutsByDate.add(workout)
            }
        }

        return sortedWorkouts
    }
}