package com.virtualathlete.myfitness.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

/**
 * Created by haris on 2018-01-19.
 */
@IgnoreExtraProperties
class Workout constructor() : BaseWorkout() {

    constructor(key: String, name: String, date: Long, workoutType: WorkoutType) : this() {
        this.key = key
        this.name = name
        this.date = date
        this.type = workoutType
    }

    constructor(name: String, date: Long, workoutType: WorkoutType) : this() {
        this.name = name
        this.date = date
        this.type = workoutType
    }

    var key: String? = null
    var name: String? = null
    var date: Long? = null
    var type: WorkoutType? = null
}