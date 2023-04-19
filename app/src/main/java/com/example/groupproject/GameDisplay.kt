package com.example.groupproject

import android.graphics.Rect
import com.example.groupproject.gameobject.GameObject


/**
 * This class was meant to be used to render screens dynamically
 *
 */
class GameDisplay(
    private val widthPixels: Int,
    private val heightPixels: Int,
    centerObject: GameObject
) {
    val DISPLAY_RECT: Rect = Rect(0, 0, widthPixels, heightPixels)
    private val centerObject: GameObject
    private val displayCenterX: Double
    private val displayCenterY: Double
    private var gameToDisplayCoordinatesOffsetX = 0.0
    private var gameToDisplayCoordinatesOffsetY = 0.0
    private var gameCenterX = 0.0
    private var gameCenterY = 0.0

    init {
        this.centerObject = centerObject
        displayCenterX = widthPixels / 2.0
        displayCenterY = heightPixels / 2.0
        update()
    }

    fun update() {
        gameCenterX = centerObject.positionX
        gameCenterY = centerObject.positionY
        gameToDisplayCoordinatesOffsetX = displayCenterX - gameCenterX
        gameToDisplayCoordinatesOffsetY = displayCenterY - gameCenterY
    }

    fun gameToDisplayCoordinatesX(x: Double): Double {
        return x + gameToDisplayCoordinatesOffsetX
    }

    fun gameToDisplayCoordinatesY(y: Double): Double {
        return y + gameToDisplayCoordinatesOffsetY
    }

    val gameRect: Rect
        get() = Rect(
            (gameCenterX - widthPixels / 2).toInt(),
            (gameCenterY - heightPixels / 2).toInt(),
            (gameCenterX + widthPixels / 2).toInt(),
            (gameCenterY + heightPixels / 2).toInt()
        )
}