package com.example.groupproject.gameobject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.example.groupproject.GameDisplay


abstract class Circle(
    context: Context?,
    color: Int,
    positionX: Double,
    positionY: Double,
    var radius: Double
) : GameObject(positionX, positionY) {
    protected var paint: Paint = Paint()

    init {
        // Set colors of circle
        paint.color = color
    }

    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        canvas.drawCircle(
            gameDisplay.gameToDisplayCoordinatesX(positionX).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(positionY).toFloat(), radius.toFloat(),
            paint
        )
    }
    open fun isColliding(obj1: Circle, obj2: Circle): Boolean {
        val distance = getDistanceBetweenObjects(obj1, obj2)
        val distanceToCollision: Double = obj1.radius + obj2.radius
        return distance < distanceToCollision
    }

    companion object {
        fun isColliding(obj1: Circle, obj2: Circle): Boolean {
            val distance = getDistanceBetweenObjects(obj1, obj2)
            val distanceToCollision: Double = obj1.radius + obj2.radius
            //Log.d("collide", distance.toString() +" | "+distanceToCollision.toString());
            return distance < distanceToCollision
        }
    }

}