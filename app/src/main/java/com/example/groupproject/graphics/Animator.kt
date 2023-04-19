package com.example.groupproject.graphics

import android.graphics.Canvas
import com.example.groupproject.GameDisplay
import com.example.groupproject.gameobject.Ground
import com.example.groupproject.gameobject.Dino
import com.example.groupproject.gameobject.DinoState
import com.example.groupproject.gameobject.Obstacle

class Animator(private val dinoSpriteArray: Array<Sprite>, private val treeSpriteArray: Array<Sprite>, private val groundSprite: Sprite) {
    private val idxNotMovingFrame = 0
    private var idxMovingFrame = 1
    private var updatesBeforeNextMoveFrame = 0
    fun draw(canvas: Canvas, gameDisplay: GameDisplay, dino: Dino) {
        when (dino.getDinoState().state) {
            DinoState.State.NOT_MOVING -> drawFrame(
                canvas, gameDisplay, dino,
                dinoSpriteArray[idxNotMovingFrame]
            )
            DinoState.State.STARED_MOVING -> {
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                drawFrame(canvas, gameDisplay, dino, dinoSpriteArray[idxMovingFrame])
            }
            DinoState.State.IS_MOVING -> {
                updatesBeforeNextMoveFrame--
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                    toggleIdxMovingFrame()
                }
                drawFrame(canvas, gameDisplay, dino, dinoSpriteArray[idxMovingFrame])
            }
            else -> {
                drawFrame(canvas, gameDisplay, dino, dinoSpriteArray[3])
            }
        }
    }

    private fun toggleIdxMovingFrame() {
        idxMovingFrame = if (idxMovingFrame == 1) 2 else 1
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay, ground:Ground) {
        drawFrame(canvas, gameDisplay, ground, groundSprite)
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay, obstacle: Obstacle, treeType:Int) {

        when (treeType) {
            1 -> drawFrame(
                canvas, gameDisplay, obstacle,
                treeSpriteArray[0]
            )
            2 -> {
                drawFrame(canvas, gameDisplay, obstacle, treeSpriteArray[1])
            }
            3 -> {
                drawFrame(canvas, gameDisplay, obstacle, treeSpriteArray[2])
            }
            else -> {}
        }
    }


    fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, obstacle: Obstacle, obstacleSpirit: Sprite) {
        obstacleSpirit.draw(
            canvas,
            (obstacle.positionX).toInt()

            ,(obstacle.positionY)
                .toInt()
        )

    }

    private fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, ground: Ground, groundSprite: Sprite) {
        groundSprite.draw(
            canvas,
            (ground.positionX).toInt()

            ,(ground.positionY)
                .toInt()
        )
        groundSprite.draw(
            canvas,
            (ground.positionX+2000*3).toInt()

            ,(ground.positionY)
                .toInt()
        )

    }




    fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, dino: Dino, sprite: Sprite) {
        sprite.draw(
            canvas,
            (dino.positionX).toInt()

            ,(dino.positionY)
                .toInt()
        )

    }



    companion object {
        private const val MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5
    }
}