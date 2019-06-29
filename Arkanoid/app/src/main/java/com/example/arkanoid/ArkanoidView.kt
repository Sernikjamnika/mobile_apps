package com.example.arkanoid

import android.annotation.SuppressLint
import android.app.Activity

import android.content.Context

import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast




@SuppressLint("ViewConstructor")
class ArkanoidView(context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private var thread : ArkanoidThread
    var bricks = arrayListOf<ArkanoidRectangle>()
    private val brickColumns = 4
    private val brickRows = 4
    var lives = 3


    lateinit var paddle: Paddle
    lateinit var ball: Ball


    init {
        holder.addCallback(this)
        thread = ArkanoidThread(holder, this)

        for (i in 1..brickRows) {
            for (j in 1..brickColumns) {
                bricks.add(Brick(0f,0f, 0f, 0f,255, 255, 255))
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val brickPadding = 10
        val brickWidth = width / brickColumns - brickPadding
        val brickHeight = (height - (brickPadding * brickRows)) / (brickRows * 3)
        for (i in 0 until brickRows) {
            for (j in 0 until brickColumns) {
                val brick = bricks[4 * i + j]
                if (brick.isVisible) {
                    val x = brickPadding + brickWidth * j
                    val y = brickPadding + brickHeight * i
                    brick.x = x.toFloat()
                    brick.y = y.toFloat()
                    brick.height = (brickHeight - 2 * brickPadding).toFloat()
                    brick.width = (brickWidth - 2 * brickPadding).toFloat()
                }

            }
        }
        thread.setRunning(true)
        thread.start()
    }

    fun update() {
        ball.checkScreenCollision(width, height)
        ball.checkObjectsCollision((bricks + paddle as ArkanoidRectangle) as ArrayList<ArkanoidRectangle>)
        ball.updatePosition()
        if (ball.checkHitBottom(height) && lives >= 1) {
            lives -= 1
        }
        paddle.checkScreenCollision(width)
        paddle.updatePosition()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return
        // draw ball
        ball.draw(canvas)

        // draw bricks
        drawBricks(canvas)

        // draw paddle
        paddle.y = height.toFloat() - paddle.height
        paddle.draw(canvas)

        // draw lives, win or lose communicate
        if (lives > 0){
            if (checkWin()){
                win(canvas)
            } else{
                drawText(canvas, "Number of lives: $lives")
            }
        } else{
            lose(canvas)
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            // Player has removed finger from screen

            MotionEvent.ACTION_UP -> {
                paddle.dx = 0f
            }
            // Player has touched the screen
            MotionEvent.ACTION_DOWN -> {
                if (event.x > width / 2) {
                    paddle.dx = 20f
                } else {
                    paddle.dx = -20f
                }
                return true
            }


        }


        return super.onTouchEvent(event)
    }

    private fun drawBricks(canvas: Canvas){
        for (brick in bricks) {
            if (brick.isVisible) {
                brick.draw(canvas)
            }
        }
    }

    private fun drawText(canvas: Canvas, text: String) {
        val paint = Paint()
        paint.setARGB(255, 255, 255, 255)
        paint.textSize = 80f
        paint.textAlign = Paint.Align.CENTER
        val textHeight = height / 2 + paint.descent() - paint.ascent()
        canvas.drawText(text, (width / 2).toFloat(), textHeight, paint)
    }

    private fun checkWin(): Boolean{
        for (brick in bricks) {
            if (brick.isVisible) {
                return false
            }
        }
        return true
    }

    private fun lose(canvas: Canvas) {
        lives = 3
        restart()

        (context as Activity).runOnUiThread(Runnable {
            Toast.makeText(context as Activity, "YOU LOST. RESTARTED", Toast.LENGTH_SHORT).show()
        })
    }

    private fun win(canvas: Canvas){
        lives = 3
        restart()

        (context as Activity).runOnUiThread(Runnable {
            Toast.makeText(context as Activity, "YOU WON. RESTARTED", Toast.LENGTH_SHORT).show()

        })
    }

    private fun restart(){
        for (brick in bricks) {
            brick.isVisible = true
        }
    }
}
