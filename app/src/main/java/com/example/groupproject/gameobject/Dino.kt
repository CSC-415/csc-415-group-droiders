package com.example.groupproject.gameobject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.groupproject.GameDisplay
import com.example.groupproject.GameLoop
import com.example.groupproject.R
import com.example.groupproject.graphics.Animator

class Dino(
    context: Context,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator
) :
    Circle(context, ContextCompat.getColor(context, R.color.purple_700), positionX, positionY, radius) {
    private val animator: Animator
    private var dinoState: DinoState
    private var jumpState: Boolean
    private var isDead: Boolean

    init {
        this.animator = animator
        this.dinoState = DinoState(this)
        this.jumpState = false
        this.isDead = false
    }

    fun getIsDead(): Boolean {
        return isDead
    }
    fun kill(){
        isDead = true;
    }


    override fun update() {
        // Update position
        //gravity
        //base = 816
        // y = 0.5gt² + v't
        if(jumpState){
            positionY -= velocityY
            if(positionY <= 200) {
                velocityY = -10.0
            }
            if(positionY>=660.0){
                positionY=660.0
                velocityY=0.0;
                jumpState = false
            }
        }



        //gravity

        dinoState.update()
    }


    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        animator.draw(canvas, gameDisplay, this)
    }

    fun getDinoState(): DinoState {
        return dinoState
    }

    fun jump(){
        if(!jumpState) {
            velocityY = 40.0
            jumpState = true
        }
    }


    companion object {
        const val SPEED_PIXELS_PER_SECOND = 400.0
        private val MAX_SPEED: Double = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    }
}
