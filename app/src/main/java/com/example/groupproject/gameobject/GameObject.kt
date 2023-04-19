package com.example.groupproject.gameobject

import android.graphics.Canvas
import com.example.groupproject.GameDisplay

abstract class GameObject{

    var positionX = 0.0;
    var positionY = 0.0;
    var velocityY = 0.0;
    var velocityX = 0.0;
    var directionX = 0.0;
    var directionY = 0.0;
    constructor() {}
    constructor(positionX: Double, positionY: Double) {
        this.positionX = positionX
        this.positionY = positionY
    }

    abstract fun draw(canvas: Canvas, gameDisplay: GameDisplay)

    abstract fun update()
    companion object {
        public fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject): Double {
            return Math.sqrt(
                Math.pow(obj2.positionX - obj1.positionX, 2.0) +
                        Math.pow(obj2.positionY - obj1.positionY, 2.0)
            )
        }
    }
}

