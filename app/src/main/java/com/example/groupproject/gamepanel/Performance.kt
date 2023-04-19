package com.example.groupproject.gamepanel

import com.example.groupproject.GameLoop
import com.example.groupproject.R



import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Performance(private val context: Context, private val gameLoop: GameLoop) {
    fun draw(canvas: Canvas) {
        drawUPS(canvas)
        drawFPS(canvas)
    }

    fun drawUPS(canvas: Canvas) {
        val averageUPS = java.lang.Double.toString(gameLoop.averageUPS)
        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.teal_200)
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("UPS: $averageUPS", 100f, 100f, paint)
    }

    fun drawFPS(canvas: Canvas) {
        val averageFPS = java.lang.Double.toString(gameLoop.averageUPS)
        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.teal_200)
        paint.color = color
        paint.textSize = 50f
        canvas.drawText("FPS: $averageFPS", 100f, 200f, paint)
    }
}