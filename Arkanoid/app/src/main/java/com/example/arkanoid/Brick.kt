package com.example.arkanoid

import android.graphics.Canvas
import android.graphics.RectF
import kotlin.math.max
import kotlin.math.min

class Brick(x: Float, y: Float, width: Float, height: Float, r: Int, g: Int, b: Int) : ArkanoidRectangle(
    x,
    y,
    width,
    height,
    r,
    g,
    b
) {


    override fun draw(canvas: Canvas) {
        if (isVisible){
            super.draw(canvas)
        }
    }

    override fun ballCollides(ball: Ball): Boolean {
        if (isVisible) {
            val deltaX = ball.x - max(x, min(ball.x, x + width))
            val deltaY = ball.y - max(y, min(ball.y, y + height))
            val result = (deltaX * deltaX + deltaY * deltaY) < (ball.radius * ball.radius)
            if (result) {
                isVisible = false
            }
            return result
        }
        return false
    }

}