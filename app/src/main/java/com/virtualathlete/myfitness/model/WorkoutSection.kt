package com.virtualathlete.myfitness.model

/**
 * Created by haris on 2018-01-25.
 */

class WorkoutSection constructor() : BaseWorkout(){
    constructor(date: String) : this() {
        this.date = date
    }

    lateinit var date: String
}
