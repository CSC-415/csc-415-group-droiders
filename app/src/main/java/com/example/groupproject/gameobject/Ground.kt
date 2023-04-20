package com.example.groupproject.gameobject


import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.groupproject.GameDisplay
import com.example.groupproject.GameLoop
import com.example.groupproject.R
import com.example.groupproject.graphics.Animator

class Ground(
    context: Context,
    startingX: Double,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator
) :
    Circle(context, ContextCompat.getColor(context, R.color.purple_700), positionX, positionY, radius) {
    private val animator: Animator



    init {
        this.animator = animator
    }

    override fun update() {
        velocityX = 20.0
        positionX -= velocityX
        if (positionX <= -5500) positionX = 0.0


    }


    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        animator.draw(canvas, gameDisplay, this)
    }






    companion object {
        const val SPEED_PIXELS_PER_SECOND = 600.0
        private val MAX_SPEED: Double = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    }
}



