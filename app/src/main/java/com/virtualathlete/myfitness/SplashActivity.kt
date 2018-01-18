package com.virtualathlete.myfitness

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent



/**
 * Created by haris on 2018-01-18.
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
