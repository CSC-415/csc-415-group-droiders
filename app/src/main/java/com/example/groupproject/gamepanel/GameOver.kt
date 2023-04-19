package com.example.groupproject.gamepanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.groupproject.R

/**
 * GameOver is a panel which draws the text Game Over to the screen.
 */
class GameOver(private val context: Context) {
    fun draw(canvas: Canvas) {
        val text = "Game Over"
        val x = 800f
        val y = 200f
        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.purple_200)
        paint.color = color
        val textSize = 150f
        paint.textSize = textSize
        canvas.drawText(text, x, y, paint)
    }

}