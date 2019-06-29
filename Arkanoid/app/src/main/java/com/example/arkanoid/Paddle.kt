package com.example.arkanoid

class Paddle(x: Float, y: Float, width: Float, height: Float, r: Int, g: Int, b: Int) : ArkanoidRectangle(
    x,
    y,
    width,
    height,
    r,
    g,
    b
) {
    var dx = 0f

    fun updatePosition(){
        x += dx
    }

    fun checkScreenCollision(screenWidth: Int) {
        if ((x <= 0 && dx < 0f) || (x + width >= screenWidth && dx > 0f)) {
            dx = 0f
        }
    }

    override fun ballCollides(ball: Ball): Boolean {
        val result = super.ballCollides(ball)
        if (result) {
            ball.dx += dx / 5
        }
        return result
    }

}