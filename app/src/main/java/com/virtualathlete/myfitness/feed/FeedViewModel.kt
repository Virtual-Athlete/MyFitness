package com.virtualathlete.myfitness.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.*
import com.virtualathlete.myfitness.model.BaseWorkout
import com.virtualathlete.myfitness.model.Workout
import com.virtualathlete.myfitness.model.WorkoutSection
import java.util.*


/**
 * Created by haris on 2018-01-20.
 */

class FeedViewModel: ViewModel(){
    var workouts: MutableLiveData<MutableList<BaseWorkout>> = MutableLiveData()

    private var workoutRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("workouts")

    fun getWorkouts() : LiveData<MutableList<BaseWorkout>> {
        if (workouts.value == null) {
            workoutRef
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

                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(javaClass.name, "Listener was cancelled at workouts")
                        }
                    })
        }
        return workouts
    }

    fun addWorkout(workout: Workout){
        workoutRef
                .push()
                .setValue(workout)
    }

    private fun sortWorkoutsByDate(workouts: ArrayList<Workout>) : MutableList<BaseWorkout>{

        workouts.sortedBy { workout -> workout.date }

        val sortedWorkouts: TreeMap<String, MutableList<Workout>> = TreeMap()
        workouts.forEach{ workout -> run {
                var workoutsByDate: MutableList<Workout>? = sortedWorkouts[Date(workout.date!!).time.toString()]
                if (workoutsByDate == null) {
                    workoutsByDate = ArrayList<Workout>()
                    val newDateKey: String = Date(workout.date!!).time.toString()
                    sortedWorkouts[newDateKey] = workoutsByDate
                }
            workoutsByDate.add(workout)
            }
        }

        val sortedBaseWorkouts: MutableList<BaseWorkout> = ArrayList()
        sortedWorkouts.forEach{ workouts ->
            run {
                sortedBaseWorkouts.add(WorkoutSection(workouts.key.toLong()))
                workouts.value.forEach{
                    sortedBaseWorkouts.add(Workout(it.name!!, Date(it.date!!).time, it.type!!))
                }
            }
        }

        return sortedBaseWorkouts
    }
}