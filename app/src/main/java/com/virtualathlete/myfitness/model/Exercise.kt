package com.virtualathlete.myfitness.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

/**
 * Created by haris on 2018-02-03.
 */
@IgnoreExtraProperties
class Exercise constructor() {
    constructor(name: String, reps: Int, tempo: String, workoutSetId: String) : this() {
        this.name = name
        this.reps = reps
        this.workoutSetId = workoutSetId
    }

    var name: String? = null
    var reps: Int? = null
    @PropertyName("workout_set_id")
    var workoutSetId: String? = null
}