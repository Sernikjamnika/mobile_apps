package com.example.arkanoid

import android.graphics.Canvas
import android.view.SurfaceHolder


class ArkanoidThread(private val surfaceHolder: SurfaceHolder,
                 private val gameView: ArkanoidView) : Thread() {

    private var running : Boolean = false
    private val targetFPS = 50
    private var canvas: Canvas? = null

    fun setRunning(isRunning : Boolean) {
        this.running = isRunning
    }

    override fun run() {
        var startTime : Long
        var timeMillis : Long
        var waitTime : Long
        val targetTime = (1000 / targetFPS).toLong()

        while(running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000

            waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}