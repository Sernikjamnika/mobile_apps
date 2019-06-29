package com.example.arkanoid

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.max
import kotlin.math.min

open class ArkanoidRectangle(var x: Float, var y: Float, var width: Float, var height: Float, r: Int, g: Int, b: Int){
    private val color: Paint = Paint()
    var isVisible = true
    init {
        color.setARGB(255, r, g, b)
    }

    open fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, color)
    }

    open fun ballCollides(ball: Ball): Boolean {
        val deltaX = ball.x - max(x, min(ball.x, x + width))
        val deltaY = ball.y - max(y, min(ball.y, y + height))
        return (deltaX * deltaX + deltaY * deltaY) < (ball.radius * ball.radius)
    }
}