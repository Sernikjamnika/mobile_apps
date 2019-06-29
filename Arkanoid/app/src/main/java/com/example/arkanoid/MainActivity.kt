package com.example.arkanoid

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        buildBoard(size.x.toFloat(), size.y.toFloat())

    }

    private fun buildBoard(width: Float, height: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val gson = Gson()

        arkanoidView.ball = Ball(width / 2, height / 2, 50f, 255, 255, 255)
        arkanoidView.paddle = Paddle(width / 2 , height,300f, 50f, 255, 255, 255)

        // get lives
        arkanoidView.lives = sharedPref.getInt(getString(R.string.lives), 3)

        // get ball
        val ball = sharedPref.getString(getString(R.string.ball), null)
        if (ball == null) {
            arkanoidView.ball = Ball(width / 2, height / 2, 50f, 255, 255, 255)
        } else {
            arkanoidView.ball = gson.fromJson<Ball>(ball, Ball::class.java)
        }

       // get paddle
        val paddle = sharedPref.getString(getString(R.string.paddle), null)
        if (paddle == null) {
            arkanoidView.paddle = Paddle(width / 2 , height,300f, 50f, 255, 255, 255)
        } else {
            arkanoidView.paddle = gson.fromJson<Paddle>(paddle, Paddle::class.java)
        }

        arkanoidView.bricks.forEachIndexed { index, brick ->
            val brickSharedPref = sharedPref.getBoolean("brick$index", true)
            brick.isVisible = brickSharedPref

        }

    }

    private fun saveGameState() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val gson = Gson()

        with (sharedPref.edit()) {
            putInt(getString(R.string.lives), arkanoidView.lives)

            // ball
            var json = gson.toJson(arkanoidView.ball)
            putString(getString(R.string.ball), json)

            // paddle
            json = gson.toJson(arkanoidView.paddle)
            putString(getString(R.string.paddle), json)


            arkanoidView.bricks.forEachIndexed { index, brick ->
                putBoolean("brick$index", brick.isVisible)
            }


            apply()
        }
    }

    override fun onStop() {
        saveGameState()
        super.onStop()
    }
}
