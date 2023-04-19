package com.example.groupproject.gameobject

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.groupproject.GameDisplay
import com.example.groupproject.GameLoop
import com.example.groupproject.R
import com.example.groupproject.graphics.Animator

public class Obstacle(
    context: Context,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator,
    dino:Dino

) :
    Circle(context, ContextCompat.getColor(context, R.color.purple_700), positionX, positionY, radius) {
    private val animator: Animator
    private val type: Int





    init {
        this.animator = animator
        this.type = (1..3).random()
    }

    override fun update() {
        velocityX = 20.0
        positionX -= velocityX


    }




    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        animator.draw(canvas, gameDisplay, this, type)
    }




    companion object {
        private val SPEED_PIXELS_PER_SECOND: Double = Dino.SPEED_PIXELS_PER_SECOND * 1.0
        private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private val SPAWNS_PER_MINUTE = 20.0
        private val SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0
        private val UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND
        private var updatesUntilNextSpawn = UPDATES_PER_SPAWN
        public fun readyToSpawn(): Boolean {
            return if (updatesUntilNextSpawn <= 0) {
                updatesUntilNextSpawn += UPDATES_PER_SPAWN
                true
            } else {
                updatesUntilNextSpawn--
                false
            }
        }
    }
}
