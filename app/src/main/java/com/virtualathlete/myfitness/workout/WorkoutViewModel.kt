package com.virtualathlete.myfitness.workout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.*
import com.virtualathlete.myfitness.model.Workout
import java.util.*
import kotlin.collections.ArrayList

class WorkoutViewModel: ViewModel(){
    var workout: MutableLiveData<Workout> = MutableLiveData()

    private var workoutRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("workouts").child(Calendar.getInstance().time.toString())

    fun getWorkout() : LiveData<Workout> {
        if (workout.value == null) {
            workoutRef
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val mutableWorkouts = ArrayList<Workout>()
                                dataSnapshot.children
                                        .map { it.getValue<Workout>(Workout::class.java) }
                                        .forEach { workout -> workout?.let { mutableWorkouts.add(it) } }
                                workout.postValue(mutableWorkouts.firstOrNull())
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(javaClass.name, "Listener was cancelled at workouts")
                        }
                    })
        }
        return workout
    }
}