package com.example.arkanoid

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class Ball(var x: Float, var y: Float, val radius: Float, r: Int, g: Int, b: Int) {

    var dx = 10.0F
    private var dy = 10.0F
    private val color: Paint = Paint()

    init {
        color.setARGB(255, r, g, b)
    }

    fun draw(canvas: Canvas) {
        canvas.drawOval(RectF(x, y,x + radius,y + radius), color)
    }

    fun updatePosition() {
        x += dx
        y += dy
    }

    fun checkScreenCollision(screenWidth: Int, screenHeight: Int) {
        if (x <= 0 || x + radius >= screenWidth) {
            dx = -dx
        }
        if (y <= 0 || y + radius >= screenHeight) {
            dy = -dy
        }
    }

    fun checkObjectsCollision(bricks: ArrayList<ArkanoidRectangle>) {
        for (brick in bricks) {
            if (brick.ballCollides(this)) {
                dx = -dx
                dy = -dy
                return
            }
        }
    }

   fun checkHitBottom(height: Int): Boolean {
        if (y + radius >= height) {
            return true
        }
        return false
   }
}